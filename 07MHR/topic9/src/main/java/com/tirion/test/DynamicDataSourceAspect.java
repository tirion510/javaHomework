package com.tirion.test;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect implements Ordered {

	@Pointcut("@annotation(com.tirion.test.UseHikari)")
	public void dataSourcePointCut() {

	}

	@Around("dataSourcePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		UseHikari useHikari = method.getAnnotation(UseHikari.class);
		if (useHikari == null || !useHikari.useHikari()) {
			DynamicDatasource.setDataSource("default");
			log.info("set datasource use hikari:"+useHikari.useHikari());
		} else {
			DynamicDatasource.setDataSource("hikari");
			log.info("set datasource use hikari:"+useHikari.useHikari());
		}

		try {
			return point.proceed();
		} finally {
			DynamicDatasource.clearDataSource();
			log.debug("clean datasource");
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}

