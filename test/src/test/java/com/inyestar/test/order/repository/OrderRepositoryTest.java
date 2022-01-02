package com.inyestar.test.order.repository;

import static org.junit.Assert.assertEquals;

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
	
}
