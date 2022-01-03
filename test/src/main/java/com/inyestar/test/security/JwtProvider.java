package com.inyestar.test.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inyestar.test.config.property.JwtProperty;
import com.inyestar.test.security.service.CustomUserDetails;
import com.inyestar.test.utils.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtProvider {
	
	private JwtProperty jwtConfig;
	
	public static final String BEARER = "Bearer ";
	
	public static final String ROLE = "role";

	private final byte[] secretKey;
	
	public JwtProvider(JwtProperty jwtConfig) {
		this.jwtConfig = jwtConfig;
		this.secretKey = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
	}
	
	public UserDetails userDetails(Authentication authentication) {
		return (UserDetails) authentication.getPrincipal();
	}
	
	/**
	 * 토큰 생성
	 * @param request
	 * @return
	 */
	public JwtToken createToken(UserDetails userDetails) {
		Date now = new Date();
		Date timeout =  new Date(now.getTime() + jwtConfig.getRefreshExpiration());
		return JwtToken.builder()
				.accessToken(Jwts.builder()
								.setSubject(userDetails.getUsername())
								.setIssuedAt(now)
								.claim(ROLE, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
								.setExpiration(new Date(now.getTime() + jwtConfig.getAccessExpiration()))
								.signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS512)
								.compact())
				.refreshToken(Jwts.builder()
								.setExpiration(timeout)
								.signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS512)
								.compact())
				.expirationTime(timeout.getTime())
				.build();
	}
	
	/**
	 * 토큰에서 subject 정보 획득
	 * @param token
	 * @return
	 */
	public String parseToken(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public UserDetails parseTokenToUserDetails(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return new CustomUserDetails(claims.getSubject(), 
								Collections.singleton(new SimpleGrantedAuthority(claims.get(ROLE, String.class))));
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
			return token.startsWith(BEARER) ? token.replace(BEARER, "") : null;
		} catch (UnsupportedEncodingException e) {
			log.error("{}", e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * 토큰 유효성 검증
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
}
