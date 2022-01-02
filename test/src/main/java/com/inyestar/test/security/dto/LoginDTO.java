package com.inyestar.test.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {

	private String email;
	
	private String password;
}
