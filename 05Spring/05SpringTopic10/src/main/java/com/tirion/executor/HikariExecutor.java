package com.tirion.executor;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class HikariExecutor extends UpgradeJDBCExecutor {

	@Autowired
	private HikariDataSource hikariDataSource;

	@Override
	public Connection getConnection() throws SQLException {
		// 替换掉getConnection的实现
		return hikariDataSource.getConnection();
	}
}
