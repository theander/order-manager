package com.anderson.ordermanager.infra.repository;

import com.anderson.ordermanager.infra.entities.OrdersEntity;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
	List<OrdersEntity> findAllByStatusOrStatusOrderByCreationDateAsc(StatusEnum status1, StatusEnum status2);

	Page<OrdersEntity> findAllByStatus(StatusEnum statusFilter, Pageable pageable);
}
