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
import com.inyestar.test.user.dto.UserDTO;
import com.inyestar.test.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/v1/users")
@Slf4j
public class UserController {
	
	@Autowired private UserService userService;
	
	/**
	 * 단일 회원 상세 정보 조회
	 * @param id
	 * @return
	 */
	@Operation(summary = "단일 회원 상세 정보 조회",  description = "특정 사용자의 회원 상세 정보를 조회합니다.")
	@GetMapping("/{id}")
	public UserDTO detail(  @Parameter(description = "사용자 PK")
							@PathVariable("id") long id) {
		log.debug("view detail request (id={})", id);
		return userService.findById(id);
	}
	
	/**
	 * 단일 회원의 주문 목록 조회
	 * @param id
	 * @param pageable
	 * @return
	 */
	@Operation(summary = "단일 회원의 주문 목록 조회", description = "특정 사용자의 전체 주문 내역을 조회합니다.")
	@GetMapping("/{id}/orders")
	public Page<OrderDTO> orders(	@Parameter(description = "사용자 PK")
									@PathVariable("id") long id, 
									@Parameter(description = "페이징 파라미터")
									Pageable pageable) {
		log.debug("view orders request (id={}, pageable={})", id, pageable);
		return userService.findOrdersByUserId(id, pageable);
	}
	
	/**
	 * 여러 회원 목록 조회
	 * @param search
	 * @param pageable
	 * @return
	 */
	@Operation(summary = "여러 회원 목록 조회", description = "다수 회원 목록을 조회합니다. 각 회원 정보에는 마지막 주문 내용이 포함되어 있습니다.")
	@GetMapping()
	public Page<UserDTO> search(	@Parameter(description="이름 혹은 이메일 검색어")
									@RequestParam("search") String search,
									@Parameter(description="페이징 파라미터")
									Pageable pageable) {
		log.debug("search request (search={}, pageable={})", search, pageable);
		return userService.searchByNameOrEmail(search, pageable);
	}
	
	/**
	 * 유저 생성
	 * @param newUser
	 * @return
	 */
	@Operation(summary = "유저 생성", description = "회원 가입 시 유저 정보를 새롭게 생성하여 저장합니다.")
	@PostMapping()
	public ResponseEntity<UserDTO> join(	@Parameter(description="신규 유저 정보")
											@RequestBody @Valid UserDTO newUser) {
		log.debug("join request (email={})", newUser.getEmail());
		UserDTO createdUser = userService.save(newUser);
		return ResponseEntity
				.created(URI.create("/v1/users/" + createdUser.getId()))
				.body(createdUser);
	}
	
}
