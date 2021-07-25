package com.tirion.other;

import com.tirion.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyHelper {

	@Autowired
	private MyProperties myProperties;

	public String getName(){
		return myProperties.getName();
	}

}
