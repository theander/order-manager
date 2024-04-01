package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.exception.custom.DeleteViolationException;
import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.app.exception.custom.UniqueConstraintViolationException;
import com.anderson.ordermanager.app.gateways.UserGateway;
import com.anderson.ordermanager.infra.entities.UsersEntity;
import com.anderson.ordermanager.infra.mapper.UserMapper;
import com.anderson.ordermanager.infra.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersRepositoryService implements UserGateway {
	private final UsersRepository usersRepository;
	private final UserMapper mapper;

	public UsersRepositoryService(UsersRepository usersRepository, UserMapper mapper) {
		this.usersRepository = usersRepository;
		this.mapper = mapper;
	}

	public Users save(Users users) {
		try {
			return mapper.toDomain(usersRepository.save(mapper.toEntity(users)));
		} catch (DataIntegrityViolationException e) {
			throw new UniqueConstraintViolationException("Conflict!!!. User Already exists.");
		}
	}

	public Users findById(Long id) {
		UsersEntity user = usersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return mapper.toDomain(user);

	}

	public void deleteById(Long id) {
		try {
			usersRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DeleteViolationException("Delete not allowed due to entity association");
		}
	}

	public Page<Users> findAll(Pageable pageable) {
		Page<UsersEntity> list = usersRepository.findAll(pageable);
		return list.map(mapper::toDomain);
	}

	@Override
	public void update(Users user) {
		try {
			usersRepository.save(mapper.toEntity(user));
		} catch (DataIntegrityViolationException e) {
			throw new UniqueConstraintViolationException("Conflict!!!. User Already exists.");
		}
	}
}
