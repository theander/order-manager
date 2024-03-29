package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.infra.entities.StatusEnum;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static com.anderson.ordermanager.app.entity.StatusEnum.*;


public class BusinessService {
	@Value("${app.email-address-from.email}")
	private String EMAIL_FROM;
	private final StockMovementService stockMovementService;
	private final OrderService orderService;
	private final EmailService emailService;
	private final UserService userService;
	private final LogWriteFileService logger;


	public BusinessService(StockMovementService stockMovementService, OrderService orderService, EmailService emailService, UserService userService, LogWriteFileService logger) {
		this.stockMovementService = stockMovementService;
		this.orderService = orderService;
		this.emailService = emailService;
		this.userService = userService;
		this.logger = logger;
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
		logger.writeLog("Order id: " + order.getId() + " was processed and not satisfied !!!");
	}

	private void finishStockMovement(List<StockMovement> stockMovementList) {
		stockMovementList.forEach(stockMovement -> {
			stockMovement.setStatus(DONE);
			stockMovementService.update(stockMovement.getId(), stockMovement);
			logger.writeLog("StockMovement was generated: " + stockMovement);
		});
	}

	private void completeOrder(Orders order) {
		order.setStatus(DONE);
		Orders updateOrder = orderService.update(order.getId(), order);
		Users userById = userService.findById(updateOrder.getUser().getId());
		logger.writeLog("Order id: " + order.getId() + " " + order + " was completed !!!");
		emailService.sendEmail(EmailDto.builder()
				.from(EMAIL_FROM)
				.to(userById.getEmail())
				.subject("Order Manager - Completed")
				.body("Order id:" + updateOrder.getId() + " quantity: " + updateOrder.getQuantity() + " was completed!!!")
				.build());
		logger.writeLog("Email was sent to: " + userById.getEmail());
	}

	private void resizeStock(long diff, List<StockMovement> stockMovementList) {
		StockMovement sm = new StockMovement();
		Item i = new Item();
		i.setId(stockMovementList.get(0).getItem().getId());
		sm.setItem(i);
		sm.setQuantity(diff);
		StockMovement stockMovement = stockMovementService.create(sm);
		logger.writeLog("StockMovement " + stockMovement + " was created due to resizing");

	}

}
