package com.anderson.ordermanager.repository;

import com.anderson.ordermanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
