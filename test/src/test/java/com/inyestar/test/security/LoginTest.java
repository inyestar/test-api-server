package com.inyestar.test.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyestar.test.auth.dto.LoginDTO;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

	@Autowired private MockMvc mockMvc;
	
	@Test
	@DisplayName("로그인 성공")
	void loginSuccessTest() throws Exception {
		
		mockMvc.perform(
				post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new LoginDTO("sdasdf@naver.com", "sdDaf1sd#fasd")))
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("로그인 실패 - 없는 사용자")
	void loginFailWithNoUserTest() throws Exception {
		
		mockMvc.perform(
				post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new LoginDTO("kkkkkk@naver.com", "sdDaf1sd#fasd")))
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().is(401));
	}
	
	@Test
	@DisplayName("로그인 실패 - 비밀번호 틀림")
	void loginFailWithInvalidPasswordTest() throws Exception {
		
		mockMvc.perform(
				post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new LoginDTO("xerfds5z@naver.com", "sdDaf1sd#fasd")))
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().is(401));
	}
}
