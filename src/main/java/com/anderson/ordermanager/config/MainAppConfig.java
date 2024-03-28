package com.anderson.ordermanager.config;

import com.anderson.ordermanager.app.service.*;
import com.anderson.ordermanager.infra.mapper.StockMovementMapper;
import com.anderson.ordermanager.infra.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainAppConfig {
	@Bean
	public UserService userService(UsersRepositoryService usersRepositoryService) {
		return new UserService(usersRepositoryService);
	}

	@Bean
	public StockMovementService stockMovementService(StockMovementRepositoryService stockMovementRepositoryService) {
		return new StockMovementService(stockMovementRepositoryService);
	}

	@Bean
	public OrderService orderService(OrderRepositoryService orderRepositoryService) {
		return new OrderService(orderRepositoryService);
	}

	@Bean
	public LogService logService() {
		return new LogService();
	}

	@Bean
	public ItemService itemService(ItemRepositoryService itemRepositoryService) {
		return new ItemService(itemRepositoryService);
	}

	@Bean
	public EmailService emailService(EmailServiceSender emailServiceSender) {
		return new EmailService(emailServiceSender);
	}

	@Bean
	public BusinessService businessService(StockMovementService stockMovementService, OrderService orderService, EmailService emailService, UserService userService, StockMovementMapper stockMovementMapper) {
		return new BusinessService(stockMovementService, orderService, emailService, userService, stockMovementMapper);

	}
}
