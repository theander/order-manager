package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.infra.mapper.OrderMapper;
import com.anderson.ordermanager.infra.web.dto.OrderDto;
import com.anderson.ordermanager.infra.web.dto.SortEnum;
import com.anderson.ordermanager.infra.web.pagination.Pagination;
import com.anderson.ordermanager.infra.web.pagination.PaginationResponse;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class OrderController {
	private final OrderService orderService;
	private final BusinessService businessService;
	private final Pagination pagination;
	private final OrderMapper mapper;

	public OrderController(OrderService orderService, BusinessService businessService, Pagination pagination, OrderMapper mapper) {
		this.orderService = orderService;
		this.businessService = businessService;
		this.pagination = pagination;
		this.mapper = mapper;
	}

	@PostMapping("/order")
	public ResponseEntity<Orders> createOrder(@RequestBody OrderDto orderDto) {
		Orders orders = orderService.create(mapper.toDomain(orderDto));
		businessService.satisfyTransaction();
		return new ResponseEntity<>(orders, CREATED);
	}

	@GetMapping("order/{id}")
	public ResponseEntity<Orders> findOrderById(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), OK);
	}

	@GetMapping("order")
	public ResponseEntity<?> findAll(
			@RequestParam(value = "status", required = false) StatusEnum statusFilter,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "ASC", required = false) SortEnum sortDirection) {
		Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
		Page<Orders> itemsPage = statusFilter != null ? orderService.findAllByStatus(statusFilter, pageable) : orderService.findAll(pageable);
		Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection, itemsPage);
		PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
		return ResponseEntity.ok(response);
	}

	@PutMapping("order/{id}")
	public ResponseEntity<Orders> updateOrder(@PathVariable(value = "id") Long id, @RequestBody OrderDto orderDto) {
		Orders o = orderService.update(id, mapper.toDomain(orderDto));
		return new ResponseEntity<>(o, OK);
	}

	@DeleteMapping("order/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
