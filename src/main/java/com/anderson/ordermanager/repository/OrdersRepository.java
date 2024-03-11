package com.anderson.ordermanager.repository;

import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByStatusOrStatusOrderByCreationDateAsc(StatusEnum status1, StatusEnum status2);
    List<Orders> findAllByStatus(StatusEnum statusFilter);
}
