package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.OrderDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.entity.Users;
import com.anderson.ordermanager.enums.StatusEnum;
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

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public List<Orders> findOrderByStatus(StatusEnum status, StatusEnum status1) {
        return orderRepository.findAllByStatusOrStatusOrderByCreationDateAsc(status, status1);
    }

}
