package com.inyestar.test.order.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.inyestar.test.order.entity.Order;

@SpringBootTest
class OrderRepositoryTest {

	@Autowired private OrderRepository orderRepository;
	
	@Test
	@DisplayName("user_id로 주문 내역 전체 조회")
	void findAllByUser_IdTest() {
		Page<Order> orders = orderRepository.findAllByUser_Id(1l, PageRequest.of(0, 10));
		assertEquals("DSFJAKELAFSD", orders.toList().get(0).getOrderNo());
	}
	
	@Test
	@DisplayName("user_id로 가장 마지막 주문 조회")
	void findFirstByUser_IdOrderByCreatedAtDescTest() {
		Optional<Order> order = orderRepository.findFirstByUser_IdOrderByCreatedAtDesc(1l);
		assertEquals(false, order.isEmpty());
		assertEquals("DSFJAKELAFSD", order.get().getOrderNo());
	}
	
	@Test
	@DisplayName("user_id로 가장 마지막 주문 조회 (주문 내역 없는 경우)")
	void findFirstByUser_IdOrderByCreatedAtDescTestWithNoResult() {
		Optional<Order> order = orderRepository.findFirstByUser_IdOrderByCreatedAtDesc(2l);
		assertEquals(true, order.isEmpty());
	}
	
}
