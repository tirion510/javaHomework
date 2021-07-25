package com.tirion;

import com.tirion.config.EnableSchoolConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSchoolConfiguration
@SpringBootApplication(scanBasePackages = {"com.tirion"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
