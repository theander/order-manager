package com.anderson.ordermanager.service;

import com.anderson.ordermanager.controller.dto.OrderDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.entity.Users;
import com.anderson.ordermanager.repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
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
        order.setUser(users);
        order.setItem(item);
        return orderRepository.save(order);
    }

    public Orders findById(Long id) {
        Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("order not found");
        }
        return byId.get();
    }

    public Orders update(Long id, OrderDto orderDto) {
        Orders order = findById(id);
        order.setQuantity(orderDto.getQuantity());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }
}
