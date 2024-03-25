package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.web.dto.OrderDto;
import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.infra.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrdersRepository orderRepository;

    public OrderService(OrdersRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders create(OrderDto orderDto) {
        Users users = new Users();
        users.setId(orderDto.getUserId());
        Item item = new Item();
        item.setId(orderDto.getItemId());
        Orders order = new Orders();
        order.setQuantity(orderDto.getQuantity());
        order.setStatus(StatusEnum.CREATED);
        order.setUser(users);
        order.setItem(item);
        return orderRepository.save(order);
    }

    public Orders findById(Long id) {
        Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        return byId.get();
    }

    public Orders update(Long id, OrderDto orderDto) {
        Users users = new Users();
        users.setId(orderDto.getUserId());
        Item item = new Item();
        item.setId(orderDto.getItemId());
        Orders order = findById(id);
        order.setStatus(orderDto.getStatusEnum());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Page<Orders> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
    public Page<Orders> findAllByStatus(StatusEnum statusFilter, Pageable pageable) {
        return orderRepository.findAllByStatus(statusFilter, pageable);
    }
    public List<Orders> findOrderByStatus(StatusEnum status, StatusEnum status1) {
        return orderRepository.findAllByStatusOrStatusOrderByCreationDateAsc(status, status1);
    }

}
