package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.dto.OrderDto;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.enums.StatusEnum;
import com.anderson.ordermanager.service.BusinessService;
import com.anderson.ordermanager.service.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;
    private final BusinessService businessService;

    public OrderController(OrderService orderService, BusinessService businessService) {
        this.orderService = orderService;
        this.businessService = businessService;
    }

    @PostMapping("/order")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderDto orderDto) {
        Orders orders = orderService.create(orderDto);
        businessService.satisfyTransaction();
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("order/{id}")
    public ResponseEntity<Orders> findorderById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), OK);
    }

    @GetMapping("order")
    public ResponseEntity<List<Orders>> findAll(@RequestParam(value = "status", required = false) StatusEnum statusFilter) {
        List<Orders> orderList = statusFilter != null ? orderService.findAllByStatus(statusFilter) : orderService.findAll();
        return new ResponseEntity<>(orderList, OK);
    }

    @PutMapping("order/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable(value = "id") Long id, @RequestBody OrderDto orderDto) {
        Orders o = orderService.update(id, orderDto);
        return new ResponseEntity<>(o, OK);
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
