package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.gateways.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserService {
	private final UserGateway userGateway;

	public UserService(UserGateway userGateway) {
		this.userGateway = userGateway;
	}

	public Users create(Users users) {
		return userGateway.save(users);
	}

	public Users findById(Long id) {
		return userGateway.findById(id);
	}

	public void update(Long id, Users users) {
		Users user = findById(id);
		users.setId(user.getId());
		userGateway.save(user);

	}

	public void delete(Long id) {
		userGateway.deleteById(id);
	}

	public Page<Users> findAll(Pageable pageable) {
		return userGateway.findAll(pageable);
	}
}
