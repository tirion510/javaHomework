package com.tirion.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class TestService {

	@Autowired
	private DynamicDatasource dynamicDataSource;

	@UseHikari(useHikari = true)
	public void insert() throws SQLException {
		Connection connection = null;
		try {
			connection = dynamicDataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
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

	@UseHikari(useHikari = false)
	public void insert2() throws SQLException {
		Connection connection = null;
		try {
			connection = dynamicDataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
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

}
