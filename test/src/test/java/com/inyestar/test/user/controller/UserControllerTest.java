package com.inyestar.test.user.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired private MockMvc mockMvc;
	
	@Test
	@DisplayName("단일 회원 상세 정보 조회")
	void testDetail() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/v1/users/1")
				.accept(MediaType.APPLICATION_JSON)
				);
		result.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)));
	}
}
