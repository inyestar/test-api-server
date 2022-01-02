package com.inyestar.test.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import com.inyestar.test.security.config.JwtConfig;
import com.inyestar.test.utils.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtProvider {
	
	private JwtConfig jwtConfig;
	
	public static final String BEARER = "Bearer ";
	
	public JwtProvider(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	/**
	 * 토큰 생성
	 * @param request
	 * @return
	 */
	public String createToken(Authentication authentication) {
		String email = (String) authentication.getPrincipal();
		Date now = new Date();
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + jwtConfig.getExpiration()))
				.signWith(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
				.compact();
	}
	
	/**
	 * 토큰에서 subject 정보 획득
	 * @param token
	 * @return
	 */
	public String parseToken(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(jwtConfig.getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	/**
	 * 헤더에서 토큰 획득
	 * @param request
	 * @return
	 */
	public String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		
		try {
			token = URLDecoder.decode(token, StandardCharsets.UTF_8.name());
			if(token.startsWith(BEARER)) {
				return token.replace(BEARER, "");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("{}", e.getMessage(), e);
		}
		
		return null;
	}
	
}
