package com.inyestar.test.user.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inyestar.test.order.dto.OrderDTO;
import com.inyestar.test.order.service.OrderService;
import com.inyestar.test.user.dto.UserDTO;
import com.inyestar.test.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/v1/users")
@Slf4j
public class UserController {
	
	@Autowired private UserService userService;
	@Autowired private OrderService orderService;
	
	/**
	 * 단일 회원 상세 정보 조회
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public UserDTO detail(@PathVariable("id") long id) {
		log.debug("view detail request (id={})", id);
		return userService.findById(id);
	}
	
	/**
	 * 단일 회원의 주문 목록 조회
	 * @param id
	 * @param pageable
	 * @return
	 */
	@GetMapping("/{id}/orders")
	public Page<OrderDTO> orders(@PathVariable("id") long id, Pageable pageable) {
		log.debug("view orders request (id={}, pageable={})", id, pageable);
		return orderService.findAllByUserId(id, pageable);
	}
	
	/**
	 * 여러 회원 목록 조회
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping()
	public Page<UserDTO> search(@RequestParam("search") String search, Pageable pageable) {
		log.debug("search request (search={}, pageable={})", search, pageable);
		return userService.searchByNameOrEmail(search, pageable);
	}
	
	/**
	 * 유저 생성
	 * @param newUser
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<UserDTO> join(@RequestBody @Valid UserDTO newUser) {
		log.debug("join request (email={})", newUser.getEmail());
		UserDTO createdUser = userService.save(newUser);
		return ResponseEntity
				.created(URI.create("/v1/users/" + createdUser.getId()))
				.body(createdUser);
	}
	
}
