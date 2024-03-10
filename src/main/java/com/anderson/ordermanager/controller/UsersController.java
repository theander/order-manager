package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.dto.UserDto;
import com.anderson.ordermanager.entity.Users;
import com.anderson.ordermanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    public ResponseEntity<UserDto> createuser(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Users> finduserById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(userService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<Users>> findAll() {
        List<Users> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<Object> updateuser(@PathVariable(value = "id") Long id, @RequestBody UserDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Object> deleteuser(@PathVariable(value = "id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
