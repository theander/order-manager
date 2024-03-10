package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.dto.StockMovementDto;
import com.anderson.ordermanager.entity.StockMovement;
import com.anderson.ordermanager.service.BusinessService;
import com.anderson.ordermanager.service.StockMovementService;
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
    public ResponseEntity<List<StockMovement>> findAll() {
        List<StockMovement> stockMovementList = stockMovementService.findAll();
        return new ResponseEntity<>(stockMovementList, HttpStatus.OK);
    }

    @PutMapping("stock-movement/{id}")
    public ResponseEntity<Object> updateStockMovement(@PathVariable(value = "id") Long id, @RequestBody StockMovementDto stockMovementDto) {
        stockMovementService.update(id, stockMovementDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("stock-movement/{id}")
    public ResponseEntity<Object> deleteStockMovement(@PathVariable(value = "id") Long id) {
        stockMovementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
