package com.tirion.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 尝试插入100万行订单数据
 *
 * @author tirion.yy
 * @date 2021/8/8
 **/
@Slf4j
@Component
public class DynamicDataSourceTester implements InitializingBean {

	@Autowired
	private TestService testService;

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("begin insert 1");
		testService.insert();
		log.info("begin insert 2");
		testService.insert2();
	}



}
