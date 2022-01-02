package com.inyestar.test.user.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.inyestar.test.user.entity.User;

@SpringBootTest
class UserRepositoryTest {

	@Autowired private UserRepository userRepository;
	
	@Test
	@DisplayName("전체 유저 조회")
	void findAlltest() {
		List<User> users = userRepository.findAll(PageRequest.of(0, 3)).toList();
		assertEquals("sam", users.get(1).getName());
	}
	
	@Test
	@DisplayName("유저 ID로 특정 유저 조회")
	void findByIdTest() {
		User user = userRepository.findById(1l).get();
		assertEquals("john", user.getName());
	}
	
	@Test
	@DisplayName("이름 혹은 이메일로 검색")
	void findByNameContainingTest() {
		Page<User> users = userRepository.findByNameContainingOrEmailContaining("z", "z", PageRequest.of(0, 2));
		assertEquals(3, users.getTotalElements());
	}
	
	@Test
	@DisplayName("신규 유저 등록")
	void saveTest() {
		new User();
		User newUser = userRepository.save(User.builder()
					.name("jack")
					.nickname("헐랭방구")
					.password("sdafdfsdfasd")
					.mobile(010230120)
					.email("xcvewqefdsfasdf@gmail.com")
					.createdAt(LocalDateTime.now())
					.build()
				);
		
		User savedUser = userRepository.findById(newUser.getId()).get();
		assertEquals(newUser.getId(), savedUser.getId());
		assertEquals(newUser.getEmail(), savedUser.getEmail());
	}

}
