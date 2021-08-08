package com.tirion.test;

import com.tirion.executor.HikariExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.stream.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 尝试插入100万行订单数据
 *
 * @author tirion.yy
 * @date 2021/8/8
 **/
@Slf4j
@Component
public class ShardingSphereTester implements InitializingBean {

	@Autowired
	private HikariExecutor hikariExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		Connection connection = null;
		try {
			connection = hikariExecutor.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement =
					connection.prepareStatement("insert into my_order(user_id,order_status,pay_status) values(?,?,?)");
			preparedStatement.setLong(1, 1L);
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);
			boolean res = preparedStatement.execute();
			connection.commit();
			System.out.println(res);
			PreparedStatement preparedStatement2 =
					connection.prepareStatement("select * from my_order limit 1");
			ResultSet resultSet = preparedStatement2.executeQuery();
			resultSet.close();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}


}
