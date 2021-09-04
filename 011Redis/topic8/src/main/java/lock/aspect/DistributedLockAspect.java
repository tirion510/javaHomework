package lock.aspect;

import com.cy.fdwb.common.util.method.MethodUtil;
import lock.annotation.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DistributedLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);

    @Autowired
    DistributedLockHelper distributedLockHelper;

    @Pointcut("@annotation(com.cy.fdwb.common.lock.annotation.DistributedLock)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        Method method = MethodUtil.getMethod(pjp);
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        distributedLockHelper.preProcess(method, pjp.getArgs(), distributedLock);

        // may throw exception, so wrap with finally
        // but not catch exception, throw to Upper Invoker
        try {
            return pjp.proceed();
        } finally {
            distributedLockHelper.postProcess(method, pjp.getArgs(), distributedLock);
        }
    }
}
