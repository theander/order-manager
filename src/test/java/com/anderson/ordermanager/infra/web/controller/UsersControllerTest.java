package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.service.UserService;
import com.anderson.ordermanager.infra.mapper.UserMapper;
import com.anderson.ordermanager.infra.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@ActiveProfiles("test")
class UsersControllerTest {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	UserService userService;
	@MockBean
	UserMapper mapper;

	@Test
	void createUser() throws Exception {
		UserDto userDto = this.createUserDto();
		Users userEntity = this.createUserEntity();
		String content = objectMapper.writeValueAsString(userDto);
		when(userService.create(any())).thenReturn(userEntity);
		mockMvc.perform(post("/api/user")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andReturn();
		verify(userService, times(1)).create(any());
	}
	
	@Test
	void findUserById() throws Exception {
		Users userEntity = this.createUserEntity();
		when(userService.findById(any())).thenReturn(eq(userEntity));
		mockMvc.perform(get("/api/user/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(userService, times(1)).findById(123L);
	}

	@Test
	void findAll() throws Exception {
		Page<Users> page = this.getUsersPage();
		when(userService.findAll((Pageable) any())).thenReturn(page);
		mockMvc.perform(get("/api/user")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pagination.quantity").value(3l))
				.andExpect(jsonPath("$.pagination.next").value(nullValue()))
				.andExpect(jsonPath("$.pagination.prev").value(nullValue()))
				.andExpect(jsonPath("$.items.[1].id").value(1234l))
				.andExpect(jsonPath("$.items.[1].name").value("Mark"))
				.andReturn();
	}

	@Test
	void updateUser() throws Exception {
		Users users = this.createUserEntity();
		String content = objectMapper.writeValueAsString(users);
		users.setName("Mike");
		doNothing().when(userService).update(any(), eq(users));
		mockMvc.perform(put("/api/user/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(userService, times(1)).update(any(), any());
	}

	@Test
	void deleteUser() throws Exception {
		doNothing().when(userService).delete(any());
		mockMvc.perform(delete("/api/user/{id}", 123L).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)

		).andExpect(status().isNoContent()).andReturn();
		verify(userService, times(1)).delete(123L);
	}

	private Users createUserEntity() {
		Users users = new Users();
		users.setId(123L);
		users.setName("John");
		return users;
	}

	private Page<Users> getUsersPage() {
		Users users = new Users();
		users.setId(123L);
		users.setName("John");
		Users users1 = new Users();
		users1.setId(1234L);
		users1.setName("Mark");
		Users users2 = new Users();
		users2.setId(1235L);
		users2.setName("Bob");
		List<Users> list = Arrays.asList(users, users1, users2);
		return new PageImpl<>(list);
	}

	private UserDto createUserDto() {
		UserDto userDto = new UserDto();
		userDto.setName("John");
		userDto.setEmail("john@test.com");
		return userDto;
	}
}