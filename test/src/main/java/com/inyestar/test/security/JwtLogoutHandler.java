package com.inyestar.test.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.inyestar.test.utils.StringUtils;

public class JwtLogoutHandler implements LogoutHandler {
	
	private JwtProvider jwtProvider;
	private RedisTemplate<String, Object> redisTemplate;
	
	public JwtLogoutHandler(JwtProvider jwtProvider, RedisTemplate<String, Object> redisTemplate) {
		this.jwtProvider = jwtProvider;
		this.redisTemplate = redisTemplate;
	}
	
	/*
	 * 로그아웃 요청 시 accessToken에서 이메일 값 가져와서
	 * redis에서 해당 refreshToken 삭제
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		// XXX : 인증 정보 없는 LOGOUT 요청은 그냥 무시해도 되지 않을까?
		String token = jwtProvider.retrieveToken(request);
		if(!StringUtils.isEmpty(token) && jwtProvider.isValidToken(token)) {
			redisTemplate.delete(jwtProvider.parseToken(token));
		}
	}

}
