package com.tirion.service;

import com.tirion.feign.MockFeignClient;
import com.tirion.model.MyOrderDO;
import com.tirion.manager.MyOrderManager;
import com.tirion.util.IdWorker;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyOrderServiceImpl implements MyOrderService {

	@Autowired
	private MyOrderManager myOrderManager;

	@Autowired
	private MockFeignClient mockFeignClient;

	@Autowired
	private IdWorker idWorker;

	@Override
	@HmilyTCC(confirmMethod = "confirmAddOrder", cancelMethod = "cancelAddOrder")
	public boolean addOrder() {
		MyOrderDO myOrderDO = new MyOrderDO();
		myOrderDO.setOrderId(idWorker.nextId());
		myOrderDO.setUserId(1L);
		myOrderDO.setOrderStatus(1);
		myOrderDO.setPayStatus(1);
		long res = myOrderManager.insertSelective(myOrderDO);
		mockFeignClient.mockFail();
		return res > 0;
	}

	public boolean confirmAddOrder() {
		System.out.println("confirmAddOrder");
		return false;
	}

	public boolean cancelAddOrder() {
		System.out.println("cancelAddOrder");
		//TODO delete order by order id
		return false;
	}
}
