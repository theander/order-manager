package com.anderson.ordermanager.service;

import com.anderson.ordermanager.controller.dto.StockMovementDto;
import com.anderson.ordermanager.entity.StockMovement;
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

    public void create(StockMovementDto stockMovementDto) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(stockMovementDto.getQuantity());
        stockMovementRepository.save(stockMovement);

    }

    public StockMovement findById(Long id){
        Optional<StockMovement> byId = stockMovementRepository.findById(id);
        if(byId.isEmpty()){
            throw new EntityNotFoundException("StockMovement not found");
        }
        return byId.get();
    }
    public void update(Long id, StockMovementDto stockMovementDto) {
        StockMovement stockMovement = findById(id);
        stockMovement.setQuantity(stockMovementDto.getQuantity());
        stockMovementRepository.save(stockMovement);
    }
    public void delete(Long id){
        stockMovementRepository.deleteById(id);
    }

    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }
}
