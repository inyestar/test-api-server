package com.inyestar.test.security.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.inyestar.test.exception.InvalidArgumentException;
import com.inyestar.test.exception.NotFoundException;
import com.inyestar.test.user.entity.User;
import com.inyestar.test.user.repository.UserRepository;
import com.inyestar.test.utils.StringUtils;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private BCryptPasswordEncoder encoder;
	
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
		return new CustomUserDetails(user.getEmail(), user.getPassword(), true, true, true, true, null);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public User login(String email, String password) {
		
		if(StringUtils.isEmpty(email)) {
			throw new InvalidArgumentException("Email should be not null or empty");
		}
		
		if(StringUtils.isEmpty(password)) {
			throw new InvalidArgumentException("Password should be not null or empty");
		}
		
		Optional<User> optUser = userRepository.findByEmail(email);
		if(optUser.isEmpty()) {
			throw new NotFoundException("User not found, email=" + email);
		}
		
		User user = optUser.get();
		
		if(!encoder.matches(password, user.getPassword())) {
			throw new InvalidArgumentException("password not correct");
		}
		
		// managedEntity
		// save 안해도 transaction 끝나면 저장
		user.updateLastLoginAt();
		return user;
	}
	
	static class CustomUserDetails implements UserDetails {
		
		private String username;
		private String password;
		private boolean accountNonExpired;
		private boolean accountNonLocked;
		private boolean credentialsNonExpired;
		private boolean enabled;
		private Collection<GrantedAuthority> authorities;
		
		public CustomUserDetails(String username, 
								 String password,
								 boolean accountNonExpired,
								 boolean accountNonLocked,
								 boolean credentialsNonExpired,
								 boolean enabled,
								 Collection<GrantedAuthority> authorities) {
			this.username = username;
			this.password = password;
			this.accountNonExpired = accountNonExpired;
			this.accountNonLocked = accountNonLocked;
			this.credentialsNonExpired = credentialsNonExpired;
			this.enabled = enabled;
			this.authorities = authorities;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		@Override
		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		@Override
		public boolean isEnabled() {
			return enabled;
		}
		
	}
}
