package com.anderson.ordermanager.app.service;


import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.infra.service.StockMovementRepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class StockMovementService {
    private final StockMovementRepositoryService stockMovementRepositoryService;

	public StockMovementService(StockMovementRepositoryService stockMovementRepositoryService) {
		this.stockMovementRepositoryService = stockMovementRepositoryService;
	}

	public StockMovement create(StockMovement stockMovement) {
        stockMovement.setStatus(StatusEnum.CREATED);
        return stockMovementRepositoryService.save(stockMovement);
    }

    public StockMovement findById(Long id) {
        return stockMovementRepositoryService.findById(id);
    }

    public void update(Long id, StockMovement stockMovement) {
        StockMovement byId = findById(id);
        stockMovement.setId(byId.getId());
        stockMovementRepositoryService.update(stockMovement);
    }

    public void delete(Long id) {
        stockMovementRepositoryService.deleteById(id);
    }

    public Page<StockMovement> findAll(Pageable pageable) {
        return stockMovementRepositoryService.findAll(pageable);
    }

    public Page<StockMovement> findStockMovementByStatus(StatusEnum status, Pageable pageable) {
        return stockMovementRepositoryService.findByStatusOrderByCreationDateAsc(status, pageable);
    }
    public List<StockMovement> findStockMovementByStatus(StatusEnum status) {
        return stockMovementRepositoryService.findByStatusOrderByCreationDateAsc(status);
    }
}
