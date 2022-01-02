package com.inyestar.test.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyestar.test.security.dto.LoginDTO;

/**
 * 로그인 url로 로그인을 시도할 경우
 * email과 password로 인증 후 jwt 토큰 발행
 * @author inye
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private JwtProvider jwtProvider;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String url, JwtProvider jwtProvider) {
		setAuthenticationManager(authenticationManager);
		setFilterProcessesUrl(url);
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws AuthenticationException {
		try {
			LoginDTO credential = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
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
		response.setHeader(HttpHeaders.AUTHORIZATION, JwtProvider.BEARER + jwtProvider.createToken(authResult));
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, 
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}
