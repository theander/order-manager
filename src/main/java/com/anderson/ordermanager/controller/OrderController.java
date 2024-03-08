package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.controller.dto.OrderDto;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        orderService.create(orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @GetMapping("order/{id}")
    public ResponseEntity<Orders> findorderById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("order")
    public ResponseEntity<List<Orders>> findAll() {
        List<Orders> orderList = orderService.findAll();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PutMapping("order/{id}")
    public ResponseEntity<Object> updateorder(@PathVariable(value = "id") Long id, @RequestBody OrderDto orderDto) {
        orderService.update(id, orderDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Object> deleteorder(@PathVariable(value = "id") Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
