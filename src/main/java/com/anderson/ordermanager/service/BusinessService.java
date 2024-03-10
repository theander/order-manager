package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.OrderDto;
import com.anderson.ordermanager.dto.StockMovementDto;
import com.anderson.ordermanager.entity.Orders;
import com.anderson.ordermanager.entity.StockMovement;
import com.anderson.ordermanager.enums.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);

    private final StockMovementService stockMovementService;
    private final OrderService orderService;

    public BusinessService(StockMovementService stockMovementService, OrderService orderService) {
        this.stockMovementService = stockMovementService;
        this.orderService = orderService;
    }

    public void satisfyTransaction() {
        List<Orders> orders = orderService.findOrderByStatus(StatusEnum.CREATED,StatusEnum.PENDING);
        for (Orders order : orders) {
            List<StockMovement> stockMovementList = new ArrayList<>();
            Long quantity = order.getQuantity();
            long tot = 0;
            List<StockMovement> stockMovements = stockMovementService.findStockMovementByStatus(StatusEnum.CREATED);
            for (StockMovement stockMovement : stockMovements) {
                if (tot > quantity) {
                    break;
                }
                if(stockMovement.getItem().getId().longValue() == order.getItem().getId()) {
                    stockMovementList.add(stockMovement);
                    tot += stockMovement.getQuantity();
                }
            }
            Long total = stockMovementList.stream().map(StockMovement::getQuantity).reduce(0L, Long::sum);
            if (order.getQuantity() <= total) {
                long diff = total - order.getQuantity();
                if (diff > 0) {
                    resizeStock(diff, stockMovementList);
                }
                completeOrder(order);
                finishStockMovement(stockMovementList);
            } else {
                pendingOrder(order);
            }

        }

    }

    private void pendingOrder(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setStatusEnum(StatusEnum.PENDING);
        orderDto.setQuantity(order.getQuantity());
        orderDto.setItemId(orderDto.getItemId());
        orderDto.setUserId(order.getUser().getId());
        orderService.update(order.getId(), orderDto);
        logger.info("Order id: " + order.getId() +" was processed and not satisfied !!!");
    }

    private void finishStockMovement(List<StockMovement> stockMovementList) {
        stockMovementList.forEach(stockMovement -> {
            StockMovementDto sm = new StockMovementDto();
            sm.setStatus(StatusEnum.DONE);
            sm.setQuantity(stockMovement.getQuantity());
            sm.setItemId(stockMovement.getItem().getId());
            stockMovementService.update(stockMovement.getId(), sm);
            logger.info("StockMovement was generated: " + stockMovement);
        });
    }

    private void completeOrder(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setStatusEnum(StatusEnum.DONE);
        orderDto.setQuantity(order.getQuantity());
        orderDto.setItemId(orderDto.getItemId());
        orderDto.setUserId(order.getUser().getId());
        orderService.update(order.getId(), orderDto);
        logger.info("Order id: " + order.getId() +" "+ orderDto + " was completed !!!");
    }

    private void resizeStock(long diff, List<StockMovement> stockMovementList) {
        StockMovementDto dto = new StockMovementDto();
        dto.setItemId(stockMovementList.get(0).getItem().getId());
        dto.setQuantity(diff);
        StockMovement stockMovement = stockMovementService.create(dto);
        logger.info("StockMovement " + stockMovement + " was created due to resizing");

    }

}
