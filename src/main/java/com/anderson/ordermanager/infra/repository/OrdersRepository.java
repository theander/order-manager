package com.anderson.ordermanager.infra.repository;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByStatusOrStatusOrderByCreationDateAsc(StatusEnum status1, StatusEnum status2);
    Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable);
}
