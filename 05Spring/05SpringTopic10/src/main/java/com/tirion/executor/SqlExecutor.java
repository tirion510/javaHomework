package com.tirion.executor;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlExecutor {

	void add(String s) throws SQLException;

	int delete(Long id);

	int update(Long id,String s);

	String select(Long id);

	Connection getConnection() throws SQLException;

}
