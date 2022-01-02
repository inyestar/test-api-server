package com.inyestar.test.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inyestar.test.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findAllByUser_Id(long userId, Pageable pageable);
}
