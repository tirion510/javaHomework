package com.tirion.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tirion")
public class MyProperties {

	private String name = "yy";

}
