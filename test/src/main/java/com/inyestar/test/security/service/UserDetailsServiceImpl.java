package com.inyestar.test.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.inyestar.test.exception.NotFoundException;
import com.inyestar.test.user.entity.User;
import com.inyestar.test.user.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired private UserRepository userRepository;
	
	/**
	 * SpringSecurity에서 사용자 정보를 가져오는 부분
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByEmail(username);
	}

	private UserDetails findByEmail(String email) {
		
		User user = userRepository
				.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found, email=" + email));
		
		// TODO: 계정 만료, 비밀번호 만료, 비밀번호 횟수 초과 시 locked 구현 필요
		return new CustomUserDetails(user.getEmail(), user.getPassword());
	}
}
