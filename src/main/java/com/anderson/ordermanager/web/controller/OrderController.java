package com.anderson.ordermanager.web.controller;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.web.dto.OrderDto;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.OrderService;
import com.anderson.ordermanager.web.dto.SortEnum;
import com.anderson.ordermanager.web.pagination.Pagination;
import com.anderson.ordermanager.web.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class OrderController {
	private final OrderService orderService;
	private final BusinessService businessService;

	public OrderController(OrderService orderService, BusinessService businessService) {
		this.orderService = orderService;
		this.businessService = businessService;
	}

	@PostMapping("/order")
	public ResponseEntity<Orders> createOrder(@RequestBody OrderDto orderDto) {
		Orders orders = orderService.create(orderDto);
		businessService.satisfyTransaction();
		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}

	@GetMapping("order/{id}")
	public ResponseEntity<Orders> findorderById(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), OK);
	}

	@GetMapping("order")
	public ResponseEntity<?> findAll(
			@RequestParam(value = "status", required = false) StatusEnum statusFilter,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "status", required = false) String sortBy,
			@RequestParam(defaultValue = "ASC", required = false) SortEnum sortDirection) {
		Pagination pagination = new Pagination();
		Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
		Page<Orders> itemsPage = statusFilter != null ? orderService.findAllByStatus(statusFilter, pageable) : orderService.findAll(pageable);
		Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection.getValue(), itemsPage);
		PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
		return ResponseEntity.ok(response);
	}

	@PutMapping("order/{id}")
	public ResponseEntity<Orders> updateOrder(@PathVariable(value = "id") Long id, @RequestBody OrderDto orderDto) {
		Orders o = orderService.update(id, orderDto);
		return new ResponseEntity<>(o, OK);
	}

	@DeleteMapping("order/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
