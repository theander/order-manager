package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.StockMovementDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.entity.StockMovement;
import com.anderson.ordermanager.enums.StatusEnum;
import com.anderson.ordermanager.repository.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    public StockMovement create(StockMovementDto stockMovementDto) {
        StockMovement stockMovement = new StockMovement();
        Item item = new Item();
        item.setId(stockMovementDto.getItemId());
        stockMovement.setItem(item);
        stockMovement.setQuantity(stockMovementDto.getQuantity());
        stockMovement.setStatus(StatusEnum.CREATED);

        return stockMovementRepository.save(stockMovement);
    }

    public StockMovement findById(Long id) {
        Optional<StockMovement> byId = stockMovementRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("StockMovement not found");
        }
        return byId.get();
    }

    public void update(Long id, StockMovementDto stockMovementDto) {
        Item item = new Item();
        item.setId(stockMovementDto.getItemId());
        StockMovement stockMovement = findById(id);
        stockMovement.setQuantity(stockMovementDto.getQuantity());
        stockMovement.setStatus(stockMovementDto.getStatus());
        stockMovement.setItem(item);
        stockMovementRepository.save(stockMovement);
    }

    public void delete(Long id) {
        stockMovementRepository.deleteById(id);
    }

    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    public List<StockMovement> findStockMovementByStatus(StatusEnum status) {
        return stockMovementRepository.findByStatusOrderByCreationDateAsc(status);
    }
}
