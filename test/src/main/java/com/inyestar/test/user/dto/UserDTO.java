package com.inyestar.test.user.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inyestar.test.order.dto.OrderDTO;
import com.inyestar.test.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

	private long id;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣A-za-z]{1,20}$")
	private String name;
	
	@Pattern(regexp = "^[a-z]{1,30}$")
	private String nickname;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*\\(\\)\\-_+=])[A-Za-z\\d~!@#$%^&*\\(\\)\\-_+=]{10,}$")
	private String password;
	
	private long mobile;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(2)
	private int sex;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime createdAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime modifiedAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime lastLoginAt;
	
	@JsonManagedReference
	private OrderDTO lastOrder;
	
	public UserDTO(User user) {
		copyProperties(user, this);
	}
}
