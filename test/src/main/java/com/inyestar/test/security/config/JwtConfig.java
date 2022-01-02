package com.inyestar.test.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="jwt.token")
public class JwtConfig {

	private String secretKey;
	private int expiration;
	private String endpoint;
	
}
