package com.anderson.ordermanager.infra.repository;

import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.infra.entities.StockMovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovementEntity, Long> {
	Page<StockMovementEntity> findByStatusOrderByCreationDateAsc(StatusEnum status1, Pageable pageable);
	List<StockMovementEntity> findByStatusOrderByCreationDateAsc(StatusEnum status1);
}
