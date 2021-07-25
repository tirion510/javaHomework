package com.tirion.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class JDBCExecutor implements SqlExecutor {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public void add(String s) {
		jdbcTemplate.execute("insert into xxx(v) values('" + s + "')");
	}

	@Override
	public int delete(Long id) {
		jdbcTemplate.execute("delete from xxx where id = " + id);
		return 1;
	}

	@Override
	public int update(Long id, String s) {
		return jdbcTemplate.update("update xxx set v = '" + s + "' where id = " + id);
	}

	@Override
	public String select(Long id) {
		return jdbcTemplate.queryForObject("select v from x where id = " + id, String.class);
	}

	@Override
	public Connection getConnection() {
		return null;
	}
}
