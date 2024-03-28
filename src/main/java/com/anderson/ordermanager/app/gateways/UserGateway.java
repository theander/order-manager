package com.anderson.ordermanager.app.gateways;

import com.anderson.ordermanager.app.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserGateway {
	Users save(Users users);

	Users findById(Long id);

	void deleteById(Long id);

	Page<Users> findAll(Pageable pageable);
}
