package com.tirion.config;

import com.tirion.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {

	@Bean
	public IdWorker idWorker(){
		IdWorker idWorker = new IdWorker(1,1,100);
		return idWorker;
	}

	public static void main(String[] args) {
		System.out.println(1426595945713373184L%16);
		System.out.println(1426595946548039680L%16);
		System.out.println(1426595946694840320L%16);
		System.out.println(1426595946845835264L%16);
		System.out.println(1426595946992635904L%16);
		System.out.println(1426596145001533440L%16);
	}

}
