package com.inyestar.test.user.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyestar.test.exception.DuplicateException;
import com.inyestar.test.exception.NotFoundException;
import com.inyestar.test.user.dto.UserDTO;
import com.inyestar.test.user.entity.User;
import com.inyestar.test.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	
	@Autowired private BCryptPasswordEncoder encoder;
	
	public Page<UserDTO> findAll(Pageable pageable) {
		return userRepository
				.findAll(pageable)
				.map(UserDTO::new);
	}
	
	public UserDTO findById(long id) {
		return new UserDTO(userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found, id=" + id)));
	}
	
	public Page<UserDTO> searchByNameOrEmail(String search, Pageable pageable) {
		return userRepository
				.findByNameContainingOrEmailContaining(search, search, pageable)
				.map(UserDTO::new);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public UserDTO save(UserDTO newUser) {
		
		if (!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			throw new DuplicateException("User already exists, email=" + newUser.getEmail());
		}
		
		User createdUser = userRepository.save(User.builder()
				.name(newUser.getName())
				.nickname(newUser.getNickname())
				.password(encoder.encode(newUser.getPassword()))
				.mobile(newUser.getMobile())
				.email(newUser.getEmail())
				.sex(newUser.getSex())
				.createdAt(LocalDateTime.now())
				.build());
		
		return new UserDTO(createdUser);
	}
}
