package com.inyestar.test.security;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyestar.test.auth.dto.LoginDTO;

/**
 * 로그인 url로 로그인을 시도할 경우
 * email과 password로 인증 후 jwt 토큰 발행
 * TODO: refresh token 확인하여 재발급 요청 처리
 * @author inye
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private JwtProvider jwtProvider;
	private RedisTemplate<String, Object> redisTemplate;
	private static ObjectMapper MAPPER = new ObjectMapper();
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String url, JwtProvider jwtProvider, RedisTemplate<String, Object> redisTemplate) {
		setAuthenticationManager(authenticationManager);
		setFilterProcessesUrl(url);
		this.jwtProvider = jwtProvider;
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws AuthenticationException {
		try {
			LoginDTO credential = MAPPER.readValue(request.getInputStream(), LoginDTO.class);
			return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
								credential.getEmail(), 
								credential.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		// generate token
		UserDetails userDetails = jwtProvider.userDetails(authResult);
		JwtToken jwtToken = jwtProvider.createToken(userDetails);
		
		// store refreshToken
		redisTemplate.opsForValue().set(userDetails.getUsername(), 
										jwtToken.getRefreshToken(),
										jwtToken.getExpirationTime(), 
										TimeUnit.MICROSECONDS);
		
		// set response body
		response.resetBuffer();
		response.setStatus(HttpStatus.OK.value());
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		response.getOutputStream().print(MAPPER.writeValueAsString(jwtToken));
		response.flushBuffer();
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, 
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
}
