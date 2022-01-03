package com.inyestar.test.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="spring.redis")
@Component
public class RedisProperty {
	
	private String host;
	
	private int port;

}
