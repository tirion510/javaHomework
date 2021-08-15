package com.tirion.test;

import com.tirion.model.MyOrderDO;
import com.tirion.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
	private MyOrderManager myOrderManager;

	@Autowired
	private IdWorker idWorker;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			for (int i = 0; i < 10; i++) {
				MyOrderDO myOrderDO = new MyOrderDO();
				myOrderDO.setOrderId(idWorker.nextId());
				myOrderDO.setUserId(1L);
				myOrderDO.setOrderStatus(1);
				myOrderDO.setPayStatus(1);
				long res = myOrderManager.insertSelective(myOrderDO);
				System.out.println(res);
			}
			List<MyOrderDO> list = myOrderManager.selectByQuery(new MyOrderDO());
			System.out.println(list.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
