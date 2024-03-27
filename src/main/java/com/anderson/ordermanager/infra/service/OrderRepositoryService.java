package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.infra.entities.OrdersEntity;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.infra.mapper.OrderMapper;
import com.anderson.ordermanager.infra.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderRepositoryService {
	private final OrdersRepository ordersRepository;
	private final OrderMapper mapper;

	public OrderRepositoryService(OrdersRepository ordersRepository, OrderMapper mapper) {
		this.ordersRepository = ordersRepository;
		this.mapper = mapper;
	}

	public Orders save(Orders order) {
		OrdersEntity ordersEntity = ordersRepository.save(mapper.toDto(order));
		return mapper.toDomain(ordersEntity);
	}

	public Orders findById(Long id) {
		OrdersEntity ordersEntity = ordersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
		return mapper.toDomain(ordersEntity);
	}

	public Orders update(Orders order) {
		OrdersEntity ordersEntity = ordersRepository.save(mapper.toDto(order));
		return mapper.toDomain(ordersEntity);
	}

	public void deleteById(Long id) {
		ordersRepository.deleteById(id);
	}

	public Page<Orders> findAll(Pageable pageable) {
		Page<OrdersEntity> all = ordersRepository.findAll(pageable);
		return all.map(mapper::toDomain);
	}

	public Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable) {
		Page<OrdersEntity> all = ordersRepository.findAllByStatus(statusFilter, pageable);
		return all.map(mapper::toDomain);
	}

	public List<Orders> findAllByStatusOrStatusOrderByCreationDateAsc(StatusEnum status, StatusEnum status1) {
		List<OrdersEntity> list = ordersRepository.findAllByStatusOrStatusOrderByCreationDateAsc(status, status1);
		return list.stream().map(mapper::toDomain).toList();
	}
}
