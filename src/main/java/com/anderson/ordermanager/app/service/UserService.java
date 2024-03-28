package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.infra.service.UsersRepositoryService;
import com.anderson.ordermanager.infra.web.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserService {
	private final UsersRepositoryService usersRepositoryService;

	public UserService(UsersRepositoryService usersRepositoryService) {
		this.usersRepositoryService = usersRepositoryService;
	}

	public Users create(Users users) {
		return usersRepositoryService.save(users);
	}

	public Users findById(Long id) {
		return usersRepositoryService.findById(id);
	}

	public void update(Long id, UserDto usersDto) {
		Users user = findById(id);
		user.setName(usersDto.getName());
		user.setEmail(usersDto.getEmail());
		usersRepositoryService.save(user);

	}

	public void delete(Long id) {
		usersRepositoryService.deleteById(id);
	}

	public Page<Users> findAll(Pageable pageable) {
		return usersRepositoryService.findAll(pageable);
	}
}
