package com.inyestar.test.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyestar.test.auth.dto.LoginDTO;

@SpringBootTest
@AutoConfigureMockMvc
class LogoutTest {
	
	@Autowired private MockMvc mockMvc;
	
	@Test
	@DisplayName("로그아웃 성공")
	void logoutSuccessTest() throws Exception {
		ResultActions result = mockMvc.perform(
				post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new LoginDTO("sdasdf@naver.com", "sdDaf1sd#fasd")))
				.accept(MediaType.APPLICATION_JSON));
		
		MockHttpServletResponse response = result.andReturn().getResponse();
		JwtToken jwtToken = new ObjectMapper().readValue(response.getContentAsString(), JwtToken.class);
		mockMvc.perform(
				get("/logout")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.getAccessToken())
				)
		.andDo(print())
		.andExpect(status().isOk());
	}

}
