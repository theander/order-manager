package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessServiceTest {
	private String EMAIL_FROM;
	@Mock
	StockMovementService stockMovementService;
	@Mock
	OrderService orderService;
	@Mock
	EmailService emailService;
	@Mock
	UserService userService;
	@Mock
	LogWriteFileService logger;
	@InjectMocks
	BusinessService businessService;

	@Test
	void satisfyTransaction() {
		List<Orders> ordersList = this.getOrdersList();
		Users user = this.getOrdersList().get(0).getUser();
		Orders order = this.getOrdersList().get(0);
		order.setStatus(StatusEnum.DONE);
		List<StockMovement> stockMovements = this.getStockMovements();
		doReturn(ordersList).when(orderService).findOrderByStatus(any(), any());
		doReturn(order).when(orderService).update(any(), any());
		doReturn(stockMovements).when(stockMovementService).findStockMovementByStatus(any());
		doReturn(user).when(userService).findById(any());

		businessService.satisfyTransaction();
		verify(orderService).findOrderByStatus(any(), any());
		verify(orderService, times(3)).update(any(), any());
		verify(userService, times(3)).findById(any());
		verify(stockMovementService, times(3)).findStockMovementByStatus(any());
		verify(logger, times(9)).writeLog(any());
		verify(emailService, times(3)).sendEmail(any());

	}

	private List<Orders> getOrdersList() {
		Item item = this.getItem(123L, "Car");
		Item item1 = this.getItem(1234L, "Bike");
		Item item2 = this.getItem(1237L, "SK8");
		Users user = this.getUserList().get(0);
		Users user1 = this.getUserList().get(1);
		Users user2 = this.getUserList().get(2);

		Orders or = new Orders();
		or.setId(123L);
		or.setQuantity(10L);
		or.setItem(item);
		or.setUser(user);

		Orders or1 = new Orders();
		or1.setId(1235L);
		or1.setQuantity(15L);
		or1.setItem(item1);
		or1.setUser(user1);

		Orders or2 = new Orders();
		or2.setId(1237L);
		or2.setQuantity(15L);
		or2.setItem(item2);
		or2.setUser(user2);
		List<Orders> list = Arrays.asList(or, or1, or2);
		return list;
	}

	private List<StockMovement> getStockMovements() {
		Item item = this.getItem(123L, "Car");
		Item item1 = this.getItem(1234L, "Bike");
		Item item2 = this.getItem(1237L, "SK8");
		StockMovement sm = new StockMovement();
		sm.setId(123L);
		sm.setQuantity(10L);
		sm.setStatus(StatusEnum.CREATED);
		sm.setItem(item);

		StockMovement sm1 = new StockMovement();
		sm1.setId(1235L);
		sm1.setQuantity(15L);
		sm1.setStatus(StatusEnum.CREATED);
		sm1.setItem(item1);

		StockMovement sm2 = new StockMovement();
		sm2.setId(1237L);
		sm2.setQuantity(15L);
		sm2.setStatus(StatusEnum.CREATED);
		sm2.setItem(item2);
		return Arrays.asList(sm, sm1, sm2);

	}

	private List<Users> getUserList() {
		Users users = new Users();
		users.setId(123L);
		users.setName("jose");
		users.setEmail("jose@gmail.com");

		Users users1 = new Users();
		users1.setId(1235L);
		users1.setName("joao");
		users1.setEmail("joao@gmail.com");

		Users users2 = new Users();
		users2.setId(1237L);
		users2.setName("mike");
		users2.setEmail("mike@gmail.com");
		return Arrays.asList(users, users1, users2);
	}

	private Item getItem(Long id, String name) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		return item;
	}
}