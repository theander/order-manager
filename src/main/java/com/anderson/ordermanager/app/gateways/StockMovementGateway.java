package com.anderson.ordermanager.app.gateways;

import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockMovementGateway {
	StockMovement save(StockMovement stockMovement);

	StockMovement findById(Long id);

	void update(StockMovement stockMovement);

	void deleteById(Long id);

	Page<StockMovement> findAll(Pageable pageable);

	Page<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status, Pageable pageable);
	List<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status);
}
