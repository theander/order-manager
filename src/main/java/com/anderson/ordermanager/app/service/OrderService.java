package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.gateways.OrderGateway;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.anderson.ordermanager.app.entity.StatusEnum.CREATED;

public class OrderService {
	private final OrderGateway orderGateway;

	public OrderService(OrderGateway orderGateway) {
		this.orderGateway = orderGateway;
	}

	public Orders create(Orders order) {
		order.setStatus(CREATED);
		return orderGateway.save(order);
	}

	public Orders findById(Long id) {
		return orderGateway.findById(id);
	}

	public Orders update(Long id, Orders order) {
		Orders byId = findById(id);
		order.setId(byId.getId());
		return orderGateway.update(order);
	}

	public void delete(Long id) {
		orderGateway.deleteById(id);
	}

	public Page<Orders> findAll(Pageable pageable) {
		return orderGateway.findAll(pageable);
	}

	public Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable) {
		return orderGateway.findAllByStatus(statusFilter, pageable);
	}

	public List<Orders> findOrderByStatus(StatusEnum status, StatusEnum status1) {
		return orderGateway.findAllByStatusOrStatusOrderByCreationDateAsc(status, status1);
	}

}
