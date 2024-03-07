package com.anderson.ordermanager.repository;

import com.anderson.ordermanager.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}
