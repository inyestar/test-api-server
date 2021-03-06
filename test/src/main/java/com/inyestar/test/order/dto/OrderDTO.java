package com.inyestar.test.order.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inyestar.test.order.entity.Order;
import com.inyestar.test.user.dto.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

	private long id;
	
	private String orderNo;
	
	private String productName;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime createdAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime paidAt;
	
	@JsonBackReference
	private UserDTO user;
	
	public OrderDTO(Order order) {
		copyProperties(order, this);
	}
}
