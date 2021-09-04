package lock.aspect;

import com.cy.fdwb.common.util.method.MethodUtil;
import lock.annotation.DistributedLock;
import lock.annotation.DistributedLocks;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁处理切片
 */
@Component
@Aspect
public class DistributedLocksAspect {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLocksAspect.class);

    @Autowired
    DistributedLockHelper distributedLockHelper;

    @Pointcut("@annotation(com.cy.fdwb.common.lock.annotation.DistributedLocks)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        Method method = MethodUtil.getMethod(pjp);
        DistributedLocks distributedLocks = method.getAnnotation(DistributedLocks.class);

        for (DistributedLock distributedLock : distributedLocks.value()) {
            distributedLockHelper.preProcess(method, pjp.getArgs(), distributedLock);
        }

        // may throw exception, so wrap with finally
        // but not catch exception, throw to Upper Invoker
        try {
            return pjp.proceed();
        } finally {
            for (DistributedLock distributedLock : distributedLocks.value()) {
                distributedLockHelper.postProcess(method, pjp.getArgs(), distributedLock);
            }
        }
    }
}
