package count.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式计数器
 *
 * @author tirion.yy
 * @date 2021/9/4
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedCounter {

	String key();

	long max();
}
