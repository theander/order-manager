package com.anderson.ordermanager.web.controller;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.web.dto.SortEnum;
import com.anderson.ordermanager.web.dto.UserDto;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.service.UserService;
import com.anderson.ordermanager.web.pagination.Pagination;
import com.anderson.ordermanager.web.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public ResponseEntity<Users> createUser(@RequestBody UserDto userDto) {
		Users users = userService.create(userDto);
		return new ResponseEntity<>(users, HttpStatus.CREATED);
	}

	@GetMapping("user/{id}")
	public ResponseEntity<Users> findUserById(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(userService.findById(Long.parseLong(id)), HttpStatus.OK);
	}

	@GetMapping("user")
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "ASC", required = false) SortEnum sortDirection) {

		Pagination pagination = new Pagination();
		Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
		Page<Users> itemsPage = userService.findAll(pageable);
		Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection.getValue(), itemsPage);
		PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
		return ResponseEntity.ok(response);
	}

	@PutMapping("user/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDto userDto) {
		userService.update(id, userDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
