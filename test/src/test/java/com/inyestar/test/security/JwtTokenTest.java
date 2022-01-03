package com.inyestar.test.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inyestar.test.security.service.CustomUserDetails;

@SpringBootTest
class JwtTokenTest {

	@Autowired private JwtProvider provider;
	
	@Test
	@DisplayName("Jwt Token 생성 및 parse 테스트")
	void testToken() {
		String email = "sdasdf@naver.com";
		String token = provider.createToken(new CustomUserDetails(email, "sdDaf1sd#fasd", true, true, true, true, null)).getAccessToken();
		String subject = provider.parseToken(token);
		assertEquals(email, subject);
	}
	
}
