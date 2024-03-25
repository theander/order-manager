package com.anderson.ordermanager.infra.repository;

import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
	Page<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status1, Pageable pageable);
	List<StockMovement> findByStatusOrderByCreationDateAsc(StatusEnum status1);
}
