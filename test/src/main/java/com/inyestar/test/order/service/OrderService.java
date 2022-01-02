package com.inyestar.test.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inyestar.test.order.dto.OrderDTO;
import com.inyestar.test.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired private OrderRepository orderRepository;
	
	public Page<OrderDTO> findAllByUserId(long userId, Pageable pageable) {
		return orderRepository
				.findAllByUser_Id(userId, pageable)
				.map(OrderDTO::new);
	}
}
