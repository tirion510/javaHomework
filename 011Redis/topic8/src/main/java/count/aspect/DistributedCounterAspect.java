package count.aspect;

import count.annotation.DistributedCounter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DistributedCounterAspect {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Pointcut("@annotitation(count.annotation.DistributedCounter)")
	public void point(){}

	@Before("point() && @annotitation(distributedCounter)")
	public void before(JoinPoint joinPoint, DistributedCounter distributedCounter) throws Exception {
		// 目前只提供redis实现
		String key = distributedCounter.key();
		long count = redisTemplate.opsForValue().increment(key,1);
		if(count > distributedCounter.max()){
			throw new Exception("counter reach max");
		}
	}
}
