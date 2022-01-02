package com.inyestar.test.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.inyestar.test.security.JwtAuthenticationFilter;
import com.inyestar.test.security.JwtAuthorizationFilter;
import com.inyestar.test.security.JwtProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// to store password securely
	// ref: https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}
	
	@Bean
	public JwtProvider jwtProvider() {
		return new JwtProvider(jwtConfig());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(null)
			.and()
			.formLogin().disable()
			.logout().permitAll()
			.and()
			.authorizeHttpRequests()
			.antMatchers(HttpMethod.POST, jwtConfig().getEndpoint()).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig().getEndpoint(), jwtProvider()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtProvider()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(null).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
