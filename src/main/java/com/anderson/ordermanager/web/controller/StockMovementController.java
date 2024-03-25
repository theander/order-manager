package com.anderson.ordermanager.web.controller;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.web.dto.SortEnum;
import com.anderson.ordermanager.web.dto.StockMovementDto;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.StockMovementService;
import com.anderson.ordermanager.web.pagination.Pagination;
import com.anderson.ordermanager.web.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class StockMovementController {
	private final StockMovementService stockMovementService;
	private final BusinessService businessService;

	public StockMovementController(StockMovementService stockMovementService, BusinessService businessService) {
		this.stockMovementService = stockMovementService;
		this.businessService = businessService;
	}

	@PostMapping("stock-movement")
	public ResponseEntity<StockMovementDto> createStockMovement(@RequestBody StockMovementDto stockMovementDto) {
		stockMovementService.create(stockMovementDto);
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
			Pagination pagination = new Pagination();
			Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
			Page<StockMovement> itemsPage = statusFilter != null ? stockMovementService.findStockMovementByStatus(statusFilter, pageable) : stockMovementService.findAll(pageable);
			Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection.getValue(), itemsPage);
			PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
			return ResponseEntity.ok(response);


//		List<StockMovement> stockMovementList = statusFilter != null ?
//                stockMovementService.findStockMovementByStatus(statusFilter)
//                : stockMovementService.findAll();
//		return new ResponseEntity<>(stockMovementList, HttpStatus.OK);
	}

	@PutMapping("stock-movement/{id}")
	public ResponseEntity<Object> updateStockMovement(@PathVariable(value = "id") Long id,
                                                      @RequestBody StockMovementDto stockMovementDto) {
		stockMovementService.update(id, stockMovementDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("stock-movement/{id}")
	public ResponseEntity<Object> deleteStockMovement(@PathVariable(value = "id") Long id) {
		stockMovementService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
