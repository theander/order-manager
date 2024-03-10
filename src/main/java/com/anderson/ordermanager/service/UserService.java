package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.UserDto;
import com.anderson.ordermanager.entity.Users;
import com.anderson.ordermanager.exception.custom.DeleteViolationException;
import com.anderson.ordermanager.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.exception.custom.UniqueConstraintViolationException;
import com.anderson.ordermanager.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users create(UserDto usersDto) {
        Users users = new Users();
        users.setName(usersDto.getName());
        users.setEmail(usersDto.getEmail());
        try {
            return usersRepository.save(users);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Conflict!!!. User Already exists.");
        }
    }

    public Users findById(Long id) {
        Optional<Users> byId = usersRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return byId.get();
    }

    public void update(Long id, UserDto usersDto) {
        Users user = findById(id);
        user.setName(usersDto.getName());
        user.setEmail(usersDto.getEmail());
        usersRepository.save(user);

    }

    public void delete(Long id) {
        try {
            usersRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteViolationException("Delete not allowed due to entity association");
        }
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }
}
