package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.gateways.StockMovementGateway;
import com.anderson.ordermanager.infra.entities.StockMovementEntity;
import com.anderson.ordermanager.infra.mapper.StockMovementMapper;
import com.anderson.ordermanager.infra.repository.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementRepositoryService implements StockMovementGateway {
	private final StockMovementRepository stockMovementRepository;
private final StockMovementMapper mapper;
	public StockMovementRepositoryService(StockMovementRepository stockMovementRepository, StockMovementMapper mapper) {
		this.stockMovementRepository = stockMovementRepository;
		this.mapper = mapper;
	}

	public StockMovement save(StockMovement stockMovement) {
		return mapper.toDomain(stockMovementRepository.save(mapper.toEntity(stockMovement)));
	}

	public StockMovement findById(Long id) {
		StockMovementEntity stockMovement = stockMovementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("StockMovement not found"));
		return mapper.toDomain(stockMovement);

	}

	public void update(StockMovement stockMovement) {
		stockMovementRepository.save(mapper.toEntity(stockMovement));
	}

	public void deleteById(Long id) {
		stockMovementRepository.deleteById(id);
	}

	public Page<StockMovement> findAll(Pageable pageable) {
		Page<StockMovementEntity> all = stockMovementRepository.findAll(pageable);
		return all.map(mapper::toDomain);
	}

	public Page<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status, Pageable pageable) {
		Page<StockMovementEntity> list = stockMovementRepository.findByStatusOrderByCreationDateAsc(com.anderson.ordermanager.infra.entities.StatusEnum.valueOf(status.getValue()), pageable);
		return list.map(mapper::toDomain);
	}
	public List<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status) {
		List<StockMovementEntity> list = stockMovementRepository.findByStatusOrderByCreationDateAsc(com.anderson.ordermanager.infra.entities.StatusEnum.valueOf(status.getValue()));
		return list.stream().map(mapper::toDomain).toList();
	}
}
