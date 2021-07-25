package com.tirion.work;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpgradeJDBCExecutor implements SqlExecutor {

	@Autowired
	private JDBCExecutor jdbcExecutor;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void add(String s) throws SQLException {
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement("insert into xxx(v) values(?)");
			preparedStatement.setString(0, s);
			boolean res = preparedStatement.execute();
			System.out.println(res);
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
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
		return jdbcExecutor.select(id);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return jdbcTemplate.getDataSource().getConnection();
	}
}
