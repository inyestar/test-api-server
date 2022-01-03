package com.inyestar.test.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inyestar.test.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 20, nullable = false)
	private String name;
	
	@Column(length = 30, nullable = false)
	private String nickname;
	
	@Column(length = 30, nullable = false)
	private String password;
	
	private long mobile;
	
	@Column(length = 100, nullable = false)
	private String email;
	
	@Column
	private int sex;
	
	@Column(columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime modifiedAt;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime lastLoginAt;
	
	@Transient
	private Order lastOrder;
	
	// TODO: token 발급 성공 시 update
	public void updateLastLoginAt() {
		this.lastLoginAt = LocalDateTime.now();
	}
	
	public void setLastOrder(Order lastOrder) {
		this.lastOrder = lastOrder;
	}
}
