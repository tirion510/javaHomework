package com.tirion.other;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService implements InitializingBean {

	@Autowired
	private MyHelper myHelper;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("------------------------" + myHelper.getName());
	}
}
