package com.anderson.ordermanager.app.service;


import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.gateways.StockMovementGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class StockMovementService {

	private final StockMovementGateway stockMovementGateway;

	public StockMovementService(StockMovementGateway stockMovementGateway) {
		this.stockMovementGateway = stockMovementGateway;
	}

	public StockMovement create(StockMovement stockMovement) {
		stockMovement.setStatus(StatusEnum.CREATED);
		return stockMovementGateway.save(stockMovement);
	}

	public StockMovement findById(Long id) {
		return stockMovementGateway.findById(id);
	}

	public void update(Long id, StockMovement stockMovement) {
		StockMovement byId = findById(id);
		stockMovement.setId(byId.getId());
		stockMovementGateway.update(stockMovement);
	}

	public void delete(Long id) {
		stockMovementGateway.deleteById(id);
	}

	public Page<StockMovement> findAll(Pageable pageable) {
		return stockMovementGateway.findAll(pageable);
	}

	public Page<StockMovement> findStockMovementByStatus(StatusEnum status, Pageable pageable) {
		return stockMovementGateway.findByStatusOrderByCreationDateAsc(status, pageable);
	}

	public List<StockMovement> findStockMovementByStatus(StatusEnum status) {
		return stockMovementGateway.findByStatusOrderByCreationDateAsc(status);
	}
}
