package com.tirion.config;

import org.apache.catalina.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

	@Bean("myConfigBean")
	public Mapper mapper(){
		return new Mapper();
	}

}
