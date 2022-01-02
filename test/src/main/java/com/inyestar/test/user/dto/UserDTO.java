package com.inyestar.test.user.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.inyestar.test.order.entity.Order;
import com.inyestar.test.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

	private long id;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣A-za-z]{1,20}$")
	private String name;
	
	@Pattern(regexp = "^[a-z]{1,30}$")
	private String nickname;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*\\(\\)\\-_+=])[A-Za-z\\d~!@#$%^&*\\(\\)\\-_+=]{10,}$")
	private String password;
	
	private long mobile;
	
	@Email
	private String email;
	
	@Size(min = -1, max = 1)
	private int sex;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime createdAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime modifiedAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime lastLoginAt;
	
	private List<Order> orders;
	
	public UserDTO(User user) {
		copyProperties(user, this);
	}
}
