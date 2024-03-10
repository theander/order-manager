package com.anderson.ordermanager.repository;

import com.anderson.ordermanager.entity.StockMovement;
import com.anderson.ordermanager.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status1);
}
