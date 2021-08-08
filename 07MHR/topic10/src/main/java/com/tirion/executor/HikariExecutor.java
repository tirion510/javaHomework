package com.tirion.executor;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class HikariExecutor implements SqlExecutor {

	@Autowired
	private HikariDataSource hikariDataSource;

	@Override
	public void add(String s) throws SQLException {

	}

	@Override
	public int delete(Long id) {
		return 0;
	}

	@Override
	public int update(Long id, String s) {
		return 0;
	}

	@Override
	public String select(Long id) {
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// 替换掉getConnection的实现
		return hikariDataSource.getConnection();
	}
}
