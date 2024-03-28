package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.infra.service.OrderRepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.anderson.ordermanager.app.entity.StatusEnum.CREATED;

public class OrderService {
	private final OrderRepositoryService orderRepositoryService;

	public OrderService(OrderRepositoryService orderRepositoryService) {
		this.orderRepositoryService = orderRepositoryService;
	}

	public Orders create(Orders order) {
		order.setStatus(CREATED);
		return orderRepositoryService.save(order);
	}

	public Orders findById(Long id) {
		return orderRepositoryService.findById(id);
	}

	public Orders update(Long id, Orders order) {
		Orders byId = findById(id);
		order.setId(byId.getId());
		return orderRepositoryService.update(order);
	}

	public void delete(Long id) {
		orderRepositoryService.deleteById(id);
	}

	public Page<Orders> findAll(Pageable pageable) {
		return orderRepositoryService.findAll(pageable);
	}

	public Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable) {
		return orderRepositoryService.findAllByStatus(statusFilter, pageable);
	}

	public List<Orders> findOrderByStatus(StatusEnum status, StatusEnum status1) {
		return orderRepositoryService.findAllByStatusOrStatusOrderByCreationDateAsc(status, status1);
	}

}
