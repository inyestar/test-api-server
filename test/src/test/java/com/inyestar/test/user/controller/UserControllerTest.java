package com.inyestar.test.user.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired private MockMvc mockMvc;
	
	@Test
	@DisplayName("단일 회원 상세 정보 조회")
	@WithMockUser(username = "sdasdf@naver.com", password = "sdDaf1sd#fasd")
	void testDetail() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/v1/users/1")
				.accept(MediaType.APPLICATION_JSON)
				);
		result.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)));
	}
	
	@Test
	@DisplayName("단일 회원의 주문 목록 조회")
	@WithMockUser(username = "sdasdf@naver.com", password = "sdDaf1sd#fasd")
	void testOrders() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/v1/users/1/orders")
				.accept(MediaType.APPLICATION_JSON)
				);
		result.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].id", is(1)));
	}
	
	@Test
	@DisplayName("여러 회원 목록 조회")
	@WithMockUser(username = "sdasdf@naver.com", password = "sdDaf1sd#fasd")
	void testSearch() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/v1/users?search=z&page=0&size=3")
				.accept(MediaType.APPLICATION_JSON)
				);
		result.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content[0].id", is(1)))
		.andExpect(jsonPath("$.content[0].lastOrder.orderNo", is("DEFJ12ELAFKD")));
	}
	
	@Test
	@DisplayName("유저 생성")
	@WithMockUser(username = "sdasdf@naver.com", password = "sdDaf1sd#fasd")
	void joinTest() throws Exception {
		ResultActions result = mockMvc.perform(
				post("/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(
//						UserDTO.builder()
//							.name("kaka")
//							.nickname("dsafds1")
//							.password("dsjkslda!#3D")
//							.mobile(0124201031)
//							.email("ddddd@naver.com")
//							.build()
//						))
				.content("{\"id\":0,\"name\":\"kaka\",\"nickname\":\"dsafds\",\"password\":\"aAbced$123\", \"mobile\":22086169,\"email\":\"ddddd@naver.com\",\"sex\":1,\"createdAt\":null,\"modifiedAt\":null,\"lastLoginAt\":null,\"orders\":null}")
				.accept(MediaType.APPLICATION_JSON)
				);
		result.andDo(print())
		.andExpect(status().isCreated());
	}
}
