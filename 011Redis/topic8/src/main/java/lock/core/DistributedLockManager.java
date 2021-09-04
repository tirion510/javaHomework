package lock.core;

public interface DistributedLockManager {

    /**
     * 是否存在锁
     *
     * @params: [lockKey]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    Boolean isExist(String lockKey);

    /**
     * 添加锁
     *
     * @params: [lockKey, seconds]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    Boolean add(String lockKey, Long seconds);

    /**
     * 移除锁
     *
     * @params: [lockKey]
     * @return: java.lang.Boolean
     * @author: aichuan.wac
     * @datetime: 19/5/26
     */
    Boolean remove(String lockKey);
}
