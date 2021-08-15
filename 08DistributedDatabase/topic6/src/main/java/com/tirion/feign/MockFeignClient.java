package com.tirion.feign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "127.0.0.1:8080")
public interface MockFeignClient {

	@PostMapping(value = "/api/order/mock/feign/fail/1.0")
	@Hmily
	Boolean mockFail();
}
