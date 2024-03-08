package com.anderson.ordermanager.service;

import com.anderson.ordermanager.controller.dto.UserDto;
import com.anderson.ordermanager.entity.Users;
import com.anderson.ordermanager.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void create(UserDto usersDto) {
        Users users = new Users();
        users.setName(usersDto.getName());
        users.setEmail(usersDto.getEmail());
        usersRepository.save(users);
    }

    public Users findById(Long id) {
        Optional<Users> byId = usersRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Users not found");
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
        usersRepository.deleteById(id);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();

    }
}
