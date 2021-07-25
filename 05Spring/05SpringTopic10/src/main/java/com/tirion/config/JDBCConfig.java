package com.tirion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JDBCConfig {

	@Bean
	public JdbcTemplate jdbcTemplate(){
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}

	@Bean
	public DriverManagerDataSource dataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql:///spring");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

}
