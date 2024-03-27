package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.infra.mapper.StockMovementMapper;
import com.anderson.ordermanager.infra.web.dto.SortEnum;
import com.anderson.ordermanager.infra.web.dto.StockMovementDto;
import com.anderson.ordermanager.infra.web.pagination.Pagination;
import com.anderson.ordermanager.infra.web.pagination.PaginationResponse;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.StockMovementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class StockMovementController {
	private final StockMovementService stockMovementService;
	private final BusinessService businessService;
	private final Pagination pagination;
	private final StockMovementMapper mapper;

	public StockMovementController(StockMovementService stockMovementService, BusinessService businessService, Pagination pagination, StockMovementMapper mapper) {
		this.stockMovementService = stockMovementService;
		this.businessService = businessService;
		this.pagination = pagination;
		this.mapper = mapper;
	}

	@PostMapping("stock-movement")
	public ResponseEntity<StockMovementDto> createStockMovement(@RequestBody StockMovementDto stockMovementDto) {
		stockMovementService.create(mapper.toDomain(stockMovementDto));
		businessService.satisfyTransaction();
		return new ResponseEntity<>(stockMovementDto, HttpStatus.CREATED);
	}

	@GetMapping("stock-movement/{id}")
	public ResponseEntity<StockMovement> findStockMovementById(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(stockMovementService.findById(Long.parseLong(id)), HttpStatus.OK);
	}

	@GetMapping("stock-movement")
	public ResponseEntity<?> findAll(
			@RequestParam(value = "status", required = false) StatusEnum statusFilter,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "status", required = false) String sortBy,
			@RequestParam(defaultValue = "ASC", required = false) SortEnum sortDirection) {
		Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
		Page<StockMovement> itemsPage = statusFilter != null ? stockMovementService.findStockMovementByStatus(statusFilter, pageable) : stockMovementService.findAll(pageable);
		Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection, itemsPage);
		PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
		return ResponseEntity.ok(response);

	}

	@PutMapping("stock-movement/{id}")
	public ResponseEntity<Void> updateStockMovement(@PathVariable(value = "id") Long id,
	                                                @RequestBody StockMovementDto stockMovementDto) {
		stockMovementService.update(id, mapper.toDomain(stockMovementDto));
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("stock-movement/{id}")
	public ResponseEntity<Object> deleteStockMovement(@PathVariable(value = "id") Long id) {
		stockMovementService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
