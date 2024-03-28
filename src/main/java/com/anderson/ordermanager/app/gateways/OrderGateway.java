package com.anderson.ordermanager.app.gateways;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderGateway {
	Orders save(Orders order);

	Orders findById(Long id);

	Orders update(Orders order);

	void deleteById(Long id);

	Page<Orders> findAll(Pageable pageable);

	Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable);

	List<Orders> findAllByStatusOrStatusOrderByCreationDateAsc(StatusEnum status, StatusEnum status1);
}
