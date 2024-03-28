package com.anderson.ordermanager.config;

import com.anderson.ordermanager.app.gateways.*;
import com.anderson.ordermanager.app.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainAppConfig {
	@Bean
	public UserService userService(UserGateway userGateway) {
		return new UserService(userGateway);
	}

	@Bean
	public StockMovementService stockMovementService(StockMovementGateway stockMovementGateway) {
		return new StockMovementService(stockMovementGateway);
	}

	@Bean
	public OrderService orderService(OrderGateway orderGateway) {
		return new OrderService(orderGateway);
	}

	@Bean
	public LogService logService() {
		return new LogService();
	}

	@Bean
	public ItemService itemService(ItemGateway itemGateway) {
		return new ItemService(itemGateway);
	}

	@Bean
	public EmailService emailService(EmailGateway emailGateway) {
		return new EmailService(emailGateway);
	}

	@Bean
	public BusinessService businessService(StockMovementService stockMovementService, OrderService orderService, EmailService emailService, UserService userService) {
		return new BusinessService(stockMovementService, orderService, emailService, userService);

	}
}
