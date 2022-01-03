package com.inyestar.test.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="jwt.token")
@Component
public class JwtProperty {

	private String secret;
	private int refreshExpiration;
	private int accessExpiration;
	private String loginUrl;
	private String logoutUrl;
	
}
