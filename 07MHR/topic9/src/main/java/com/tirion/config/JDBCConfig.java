package com.tirion.config;

import com.tirion.test.DynamicDatasource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JDBCConfig {

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(defaultDataSource());
		return jdbcTemplate;
	}

	@Bean
	public DriverManagerDataSource defaultDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.201.46:3306/venus?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull");
		dataSource.setUsername("XXX");
		dataSource.setPassword("XXXX");
		return dataSource;
	}

	@Bean
	public HikariDataSource hikariDataSource() {
		HikariConfig configuration = new HikariConfig();
		configuration.setDriverClassName("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://192.168.201.46:3306/venus?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull");
		configuration.setUsername("XXX");
		configuration.setPassword("XXXX");
		HikariDataSource hikariDataSource = new HikariDataSource(configuration);
		return hikariDataSource;
	}

	@Bean
	public DynamicDatasource dynamicDataSource(DataSource defaultDataSource, DataSource hikariDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>(5);
		targetDataSources.put("default", defaultDataSource);
		targetDataSources.put("hikari", hikariDataSource);
		return new DynamicDatasource(defaultDataSource, targetDataSources);
	}

}
