package lock.annotation;


import lock.consts.ExceptionHandlerPolicy;
import lock.core.DistributedLockManager;
import lock.core.RedisDistributedLockManager;

import java.lang.annotation.*;


/**
 * 分布式锁
 * @author tirion.yy
 * @date 2021/9/4
 **/
@Inherited
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Repeatable(DistributedLocks.class)
public @interface DistributedLock {

    /**
     * 分组
     */
    String prefix();

    /**
     * 前缀
     */
    String key();

    /**
     * 锁定时间（秒），默认5秒
     */
    String lockTime() default "5";

    /**
     * 获取锁的等待时间（秒） 默认1秒
     */
    String waitTime() default "1";

    /**
     * 获取锁失败后的重试次数，默认100次
     */
    String maxRetry() default "100";

    /**
     * 重试sleep时长，默认50毫秒
     */
    String sleepTime() default "50";

    /**
     * 异常处理策略 默认终止，抛出异常
     */
    ExceptionHandlerPolicy exceptionHandlerPolicy() default ExceptionHandlerPolicy.ABORT;

    /**
     * 缓存存储
     */
    Class<? extends DistributedLockManager> manager() default RedisDistributedLockManager.class;
}
