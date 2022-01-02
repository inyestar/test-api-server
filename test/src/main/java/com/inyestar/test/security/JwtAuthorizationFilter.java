package com.inyestar.test.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.inyestar.test.utils.StringUtils;

/**
 * request의 authorization 헤더에서 토큰 획득하여 검증
 * @author inye
 *
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtProvider jwtProvider;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
		super(authenticationManager);
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		String token = jwtProvider.retrieveToken(request);
		if(!StringUtils.isEmpty(token)) {
			String email = jwtProvider.parseToken(token);
			if(!StringUtils.isEmpty(email)) {
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, null));
			}
		}
		
		chain.doFilter(request, response);
		
	}
}
