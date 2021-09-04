package lock.core;

import com.cy.redis.core.RedisValuesOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁管理器
 */
@Component
public class RedisDistributedLockManager implements DistributedLockManager {

    @Autowired
    private RedisValuesOperation redisValuesOperation;

    /**
     * 是否存在锁
     *
     * @param lockKey
     * @params: [lockKey]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    @Override
    public Boolean isExist(String lockKey) {
        return redisValuesOperation.exists(lockKey);
    }

    /**
     * 添加锁
     *
     * @param lockKey
     * @param seconds
     * @params: [lockKey, seconds]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    @Override
    public Boolean add(String lockKey, Long seconds) {
        return redisValuesOperation.addStringNX(lockKey, "", seconds);
    }

    /**
     * 移除锁
     *
     * @param lockKey
     * @params: [lockKey]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    @Override
    public Boolean remove(String lockKey) {
        return redisValuesOperation.delete(lockKey);
    }
}
