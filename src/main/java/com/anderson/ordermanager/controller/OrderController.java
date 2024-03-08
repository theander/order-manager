package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.controller.dto.OrderDto;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.create(orderDto), HttpStatus.CREATED);
    }

    @GetMapping("order/{id}")
    public ResponseEntity<Orders> findorderById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), OK);
    }

    @GetMapping("order")
    public ResponseEntity<List<Orders>> findAll() {
        List<Orders> orderList = orderService.findAll();
        return new ResponseEntity<>(orderList, OK);
    }

    @PutMapping("order/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable(value = "id") Long id, @RequestBody OrderDto orderDto) {
        Orders o = orderService.update(id, orderDto);
        return new ResponseEntity<>(o, OK);
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Object> deleteorder(@PathVariable(value = "id") Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
