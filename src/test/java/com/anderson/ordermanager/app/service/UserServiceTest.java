package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.gateways.UserGateway;
import com.anderson.ordermanager.infra.web.pagination.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	UserGateway userGateway;
	@InjectMocks
	UserService userService;

	@Test
	void create() {
		Users item = this.getUsers();
		doReturn(item).when(userGateway).save(any());
		Users save = userService.create(item);
		assertThat(save).isNotNull();
		verify(userGateway).save(any());
	}

	@Test
	void findById() {
		Users users = this.getUsers();
		doReturn(users).when(userGateway).findById(any());
		Users byId = userService.findById(123L);
		assertThat(byId).isNotNull();
		verify(userGateway).findById(any());
	}

	@Test
	void update() {
		Users users = this.getUsers();
		doNothing().when(userGateway).update(any());
		doReturn(users).when(userGateway).findById(any());
		userService.update(123L, users);
		verify(userGateway).update(users);
		verify(userGateway).findById(123L);
	}

	@Test
	void delete() {
		doNothing().when(userGateway).deleteById(any());
		userService.delete(123L);
		verify(userGateway).deleteById(123L);
	}

	@Test
	void findAll() {
		Page<Users> userList = this.getUserList();
		doReturn(userList).when(userGateway).findAll(any());

		Pagination pagination = new Pagination();
		Pageable pageable = pagination.createPageable(0, 100, "id", "ASC");

		Page<Users> all = userService.findAll(pageable);
		assertThat(all).isNotNull();
		assertThat(all.getContent()).hasSize(3);
		verify(userGateway).findAll(any());
	}

	private Page<Users> getUserList() {
		Users users = new Users();
		users.setId(123L);
		users.setName("jose");
		users.setEmail("jose@gmail.com");

		Users users1 = new Users();
		users1.setId(1235L);
		users1.setName("joao");
		users1.setEmail("joao@gmail.com");

		Users users2 = new Users();
		users2.setId(1237L);
		users2.setName("mike");
		users2.setEmail("mike@gmail.com");
		List<Users> list = Arrays.asList(users, users1, users2);
		return new PageImpl<>(list);
	}

	private Users getUsers() {
		Users users = new Users();
		users.setName("John");
		return users;
	}

}