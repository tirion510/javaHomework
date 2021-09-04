package lock.aspect;

import com.cy.fdwb.common.util.spel.SpELUtils;
import com.cy.fdwb.common.util.string.StringCustomUtil;
import lock.annotation.DistributedLock;
import lock.consts.ExceptionHandlerPolicy;
import lock.core.DistributedLockManager;
import lock.errorcode.DistributedLockErrorCode;
import lock.exception.DistributedLockException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class DistributedLockHelper {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockHelper.class);

    @Autowired
    private Map<String, DistributedLockManager> distributedLockManagerMap;

    /**
     * 分布式锁，前置处理
     *
     * @params: [method, distributedLock]
     * @return: void
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    public void preProcess(Method method, Object[] args, DistributedLock distributedLock) {
        String lockTimeStr = distributedLock.lockTime();
        long lockTime = Long.parseLong(lockTimeStr);
        String waitTimeStr = distributedLock.waitTime();
        long waitTime = Long.parseLong(waitTimeStr);

        String maxRetryStr = distributedLock.maxRetry();
        int maxRetry = Integer.parseInt(maxRetryStr);
        String sleepTimeStr = distributedLock.sleepTime();
        long sleepTime = Long.parseLong(sleepTimeStr);

        if (lockTime < 0 || waitTime < 0) {
            logger.error("DistributedLockTime not valid, waitTime: {}, lockTime:{}", waitTime, lockTime);
            throw new DistributedLockException(DistributedLockErrorCode.TIME_NOT_VALID);
        }

        DistributedLockManager manager = getDistributedLockManager(distributedLock);

        String lockKey = distributedLock.prefix() + SpELUtils.parseKey(
            distributedLock.key(), method, args);

        if (StringUtils.isEmpty(lockKey) || StringUtils.equals(lockKey, distributedLock.prefix())) {
            logger.error(
                "method: {} with annotation of DistributedLock,try to get lock, designated manager: {},  but provide "
                    + "an empty key, skip get lock",
                method.getName(), manager.getClass().getSimpleName());
            return;
        }

        Boolean result = getLock(manager, lockKey, waitTime, lockTime, maxRetry, sleepTime,
            distributedLock.exceptionHandlerPolicy());

        logger.info("get distributed lock, key: {}, result: {}, manager: {}", lockKey, result,
            manager.getClass().getSimpleName());

        if (!result) {
            logger.error("get distributed lock failed, key: {}, manager: {}", lockKey,
                manager.getClass().getSimpleName());
            throw new DistributedLockException(DistributedLockErrorCode.LOCK_FAILED);
        }
    }

    /**
     * 分布式锁，后置处理
     *
     * @params: [method, distributedLock]
     * @return: void
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    public void postProcess(Method method, Object[] args, DistributedLock distributedLock) {

        DistributedLockManager manager = getDistributedLockManager(distributedLock);

        String lockKey = distributedLock.prefix() + SpELUtils.parseKey(
            distributedLock.key(), method, args);

        if (StringUtils.isEmpty(lockKey) || StringUtils.equals(lockKey, distributedLock.prefix())) {
            logger.error(
                "method: {} with annotation of DistributedLock, designated manager: {}, try to release lock, but "
                    + "provide an empty key, "
                    + "skip process...",
                method.getName(), manager.getClass().getSimpleName());
            return;
        }
        Boolean result = releaseLock(manager, lockKey);

        logger.info("release distributed lock, key: {}, result: {}, manager: {}", lockKey, result,
            manager.getClass().getSimpleName());

        if (!result) {
            logger.error("release distributed lock error, key: {}, result: {}, manager: {}", lockKey, result,
                manager.getClass().getSimpleName());
        }
    }

    /**
     * 获取分布式锁manager
     *
     * @params: [distributedLock]
     * @return: com.cy.fdwb.common.lock.core.DistributedLockManager
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    private DistributedLockManager getDistributedLockManager(DistributedLock distributedLock) {
        DistributedLockManager manager = distributedLockManagerMap.get(
            StringCustomUtil.lowerFirstChar(distributedLock.manager().getSimpleName()));

        if (manager == null) {
            logger.error("DistributedLockKeyManager instance not found, className: {}",
                distributedLock.manager().getSimpleName());
            throw new DistributedLockException(DistributedLockErrorCode.MANAGER_NOT_FOUND);
        }
        return manager;
    }

    /**
     * 释放锁
     *
     * @params: [manager, lockKey]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    private Boolean releaseLock(DistributedLockManager manager, String lockKey) {
        return manager.remove(lockKey);
    }

    /**
     * 获取锁
     *
     * @params: [manager, lockKey, waitTime, lockTime]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    private Boolean getLock(DistributedLockManager manager, String lockKey, long waitTime, long lockTime, int maxRetry,
        long sleepTime, ExceptionHandlerPolicy policy) {

        long s = System.currentTimeMillis();
        long e = s + TimeUnit.SECONDS.toMillis(waitTime);

        //将大多数不等待的并发锁执行完毕
        if (ExceptionHandlerPolicy.ABORT.equals(policy) || waitTime <= 0) {
            if (!manager.isExist(lockKey)) {
                // not atomic operation, may return false
                return manager.add(lockKey, lockTime);
            } else {
                // deny wait
                if (ExceptionHandlerPolicy.ABORT.equals(policy)) {
                    throw new DistributedLockException(DistributedLockErrorCode.LOCK_KEY_DUPLICATE_AND_DENY_WAIT);
                }
                // wait timeout
                if (waitTime <= 0) {
                    throw new DistributedLockException(DistributedLockErrorCode.LOCK_KEY_DUPLICATE_AND_WAIT_TIMEOUT);
                }
            }
        }
        // 直接尝试进行一次加锁
        boolean res = manager.add(lockKey, lockTime);
        if (res) {
            return res;
        }
        int retryTimes = 0;
        // retry时长内,间隔 SLEEP_TIME 毫秒 重试;最多重试 MAX_RETRY 次
        while (e > System.currentTimeMillis() && retryTimes < maxRetry) {
            retryTimes++;

            if (manager.isExist(lockKey)) {
                logger.debug("try get distributed lock, retryTimes: {}, lockKey: {}", retryTimes, lockKey);
            } else {
                res = manager.add(lockKey, lockTime);
                if (res) {
                    return res;
                }
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e1) {
                logger.error("try get distributed lock, exception occur when wait", e);
                break;
            }
        }

        logger.error("get distributed lock fail, try time: {} or maximum waitTime: {}ms exceed limitation",
            System.currentTimeMillis() - s, retryTimes);
        throw new DistributedLockException(DistributedLockErrorCode.LOCK_KEY_DUPLICATE_AND_WAIT_TIMEOUT);
    }
}
