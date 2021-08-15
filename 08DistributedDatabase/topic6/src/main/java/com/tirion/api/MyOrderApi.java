package com.tirion.api;

import com.tirion.service.MyOrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MyOrderApi {

	@Autowired
	private MyOrderService myOrderService;

	@PostMapping("/api/order/add/1.0")
	Object addObject() {
		return myOrderService.addOrder();
	}

	@PostMapping("/api/order/mock/feign/fail/1.0")
	Boolean mockFail() {
		if (new Date().after(DateUtils.addSeconds(new Date(), -100))) {
			throw new RuntimeException("mock fail");
		}
		return false;
	}
}
