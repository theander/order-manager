package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.infra.mapper.StockMovementMapper;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import com.anderson.ordermanager.infra.web.dto.StockMovementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static com.anderson.ordermanager.app.entity.StatusEnum.*;


public class BusinessService {
	private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);
	@Value("${app.email-address-from.email}")
	private String EMAIL_FROM;
	private final StockMovementService stockMovementService;
	private final OrderService orderService;
	private final EmailService emailService;
	private final UserService userService;

	private final StockMovementMapper stockMovementMapper;

	public BusinessService(StockMovementService stockMovementService, OrderService orderService, EmailService emailService, UserService userService, StockMovementMapper stockMovementMapper) {
		this.stockMovementService = stockMovementService;
		this.orderService = orderService;
		this.emailService = emailService;
		this.userService = userService;
		this.stockMovementMapper = stockMovementMapper;
	}

	public void satisfyTransaction() {
		List<Orders> orders = orderService.findOrderByStatus(StatusEnum.CREATED, StatusEnum.PENDING);
		for (Orders order : orders) {
			List<StockMovement> stockMovementList = new ArrayList<>();
			Long quantity = order.getQuantity();
			long tot = 0;
			List<StockMovement> stockMovements = stockMovementService.findStockMovementByStatus(CREATED);
			for (StockMovement stockMovement : stockMovements) {
				if (tot > quantity) {
					break;
				}
				if (stockMovement.getItem().getId().longValue() == order.getItem().getId()) {
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
		order.setStatus(PENDING);
		orderService.update(order.getId(), order);
		logger.info("Order id: " + order.getId() + " was processed and not satisfied !!!");
	}

	private void finishStockMovement(List<StockMovement> stockMovementList) {
		stockMovementList.forEach(stockMovement -> {
			stockMovement.setStatus(DONE);
			stockMovementService.update(stockMovement.getId(), stockMovement);
			logger.info("StockMovement was generated: " + stockMovement);
		});
	}

	private void completeOrder(Orders order) {
		order.setStatus(DONE);
		Orders updateOrder = orderService.update(order.getId(), order);
		Users userById = userService.findById(updateOrder.getUser().getId());
		logger.info("Order id: " + order.getId() + " " + order + " was completed !!!");
		emailService.sendEmail(EmailDto.builder()
				.from(EMAIL_FROM)
				.to(userById.getEmail())
				.subject("Order Manager - Completed")
				.body("Order id:" + updateOrder.getId() + " quantity: " + updateOrder.getQuantity() + " was completed!!!")
				.build());
		logger.info("Email was sent to: " + userById.getEmail());
	}

	private void resizeStock(long diff, List<StockMovement> stockMovementList) {
		StockMovementDto dto = new StockMovementDto();
		dto.setItemId(stockMovementList.get(0).getItem().getId());
		dto.setQuantity(diff);
		StockMovement stockMovement = stockMovementService.create(stockMovementMapper.toDomain(dto));
		logger.info("StockMovement " + stockMovement + " was created due to resizing");

	}

}
