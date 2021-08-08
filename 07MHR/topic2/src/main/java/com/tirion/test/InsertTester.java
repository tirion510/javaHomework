package com.tirion.test;

import com.tirion.executor.HikariExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * 尝试插入100万行订单数据
 *
 * @author tirion.yy
 * @date 2021/8/8
 **/
@Slf4j
@Component
public class InsertTester implements InitializingBean {

	@Autowired
	private HikariExecutor hikariExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		// for循环一百万次插入，每次循环都单独commit
		// 结果：时间太长了，没运行完
		test1();
		// for循环一百万次插入，最后一起commit
		// 结果：时间还是很长，没运行完
		test2();
		// 批量提交1000条，分1000次执行批量插入，每次插入单独提交
		// 结果：花费了33秒
		test3();
		// 批量提交1000条，分1000次执行批量插入，最后一起提交
		// 结果：花费了15秒
		test4();
	}

	private void test1() throws SQLException {
		// for循环插入，每次都commit
		Date date = new Date();
		Connection connection = null;
		for (int i = 0; i < 1000000; i++) {
			try {
				connection = hikariExecutor.getConnection();
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
		System.out.println((new Date()).getTime() - date.getTime());
	}

	private void test2() throws SQLException {
		Date date = new Date();
		Connection connection = null;
		try {
			connection = hikariExecutor.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
			for (int i = 0; i < 1000000; i++) {
				boolean res = preparedStatement.execute();
				System.out.println(res);
			}
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
		System.out.println((new Date()).getTime() - date.getTime());
	}

	private void test3() throws SQLException {
		Date date = new Date();
		Connection connection = null;
		try {
			connection = hikariExecutor.getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
			for (int i = 0; i < 1000000; i++) {
				preparedStatement.addBatch();
				if (i % 1000 == 0) {
					int[] res = preparedStatement.executeBatch();
					System.out.println(res);
				}
				preparedStatement.clearBatch();
			}
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
		System.out.println((new Date()).getTime() - date.getTime());
	}

	private void test4() throws SQLException {
		Date date = new Date();
		Connection connection = null;
		try {
			connection = hikariExecutor.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
			for (int i = 0; i < 1000000; i++) {
				preparedStatement.addBatch();
				if (i % 1000 == 0) {
					int[] res = preparedStatement.executeBatch();
					System.out.println(res);
				}
				preparedStatement.clearBatch();
			}
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
		System.out.println((new Date()).getTime() - date.getTime());
	}

}
