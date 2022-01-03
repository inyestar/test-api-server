package com.inyestar.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.inyestar.test.config.property.JwtProperty;
import com.inyestar.test.security.JwtAuthenticationEntryPoint;
import com.inyestar.test.security.JwtAuthenticationFilter;
import com.inyestar.test.security.JwtAuthorizationFilter;
import com.inyestar.test.security.JwtLogoutHandler;
import com.inyestar.test.security.JwtProvider;
import com.inyestar.test.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private JwtProperty jwtProperty;
	@Autowired private RedisTemplate<String, Object> redisTemplate;
	
	// to store password securely
	// ref: https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtProvider jwtProvider() {
		return new JwtProvider(jwtProperty);
	}
	
	@Bean
	public UserDetailsServiceImpl userDetailsServiceImpl() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().disable()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint()) // 인증 x 요청에 대한 처리
			.and()
			.formLogin().disable()
			.logout()
			.logoutUrl(jwtProperty.getLogoutUrl())
			.addLogoutHandler(new JwtLogoutHandler(jwtProvider(), redisTemplate))
			.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
			.and()
			.authorizeHttpRequests()
			.antMatchers(
					"/v3/api-docs/**", 
					"/swagger-resources/**", 
					"/swagger-ui/**").permitAll()
			.antMatchers(HttpMethod.POST, jwtProperty.getLoginUrl()).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperty.getLoginUrl(), jwtProvider(), redisTemplate))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtProvider()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.httpBasic();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
