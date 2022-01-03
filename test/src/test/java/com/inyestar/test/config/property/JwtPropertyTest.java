package com.inyestar.test.config.property;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtPropertyTest {

	@Autowired private JwtProperty jwtConfig;
	
	@Test
	@DisplayName("설정 잘 가져오는지 확인")
	void testGetConfig() {
		assertEquals("/login", jwtConfig.getLoginUrl());
		assertEquals("/logout", jwtConfig.getLogoutUrl());
		assertEquals(12000000, jwtConfig.getRefreshExpiration());
		assertEquals(700000, jwtConfig.getAccessExpiration());
	}
}
