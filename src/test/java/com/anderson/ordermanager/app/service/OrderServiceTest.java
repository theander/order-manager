package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.gateways.OrderGateway;
import com.anderson.ordermanager.infra.web.pagination.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@Mock
	OrderGateway orderGateway;
	@InjectMocks
	OrderService orderService;

	@Test
	void create() {
		Orders orders = this.getOrders();
		doReturn(orders).when(orderGateway).save(any());
		Orders stockMovement = orderService.create(orders);
		assertThat(stockMovement).isNotNull();
		verify(orderGateway).save(any());
	}

	@Test
	void findById() {
		Orders itemToFind = this.getOrders();
		doReturn(itemToFind).when(orderGateway).findById(any());
		Orders byId = orderService.findById(123L);
		assertThat(byId).isNotNull();
		verify(orderGateway).findById(any());
	}

	@Test
	void update() {
		Orders itemToUpdate = this.getOrders();
		doReturn(itemToUpdate).when(orderGateway).findById(any());
		doReturn(itemToUpdate).when(orderGateway).update(any());
		orderService.update(123L, itemToUpdate);
		verify(orderGateway).update(itemToUpdate);
		verify(orderGateway).findById(123L);
	}

	private Orders getOrders() {
		Orders orders = new Orders();
		orders.setQuantity(10L);
		return orders;
	}

	@Test
	void delete() {
		doNothing().when(orderGateway).deleteById(any());
		orderService.delete(123L);
		verify(orderGateway).deleteById(123L);
	}

	@Test
	void findAll() {
		Page<Orders> orders = this.getOrdersMock();
		doReturn(orders).when(orderGateway).findAll(any());
		Pagination pagination = new Pagination();
		Pageable pageable = pagination.createPageable(0, 100, "id", "ASC");
		Page<Orders> all = orderService.findAll(pageable);
		assertThat(all).isNotNull();
		assertThat(all).hasSize(3);
		verify(orderGateway).findAll(pageable);
	}

	private Page<Orders> getOrdersMock() {
		Orders or = new Orders();
		or.setId(123L);
		or.setQuantity(10L);

		Orders or1 = new Orders();
		or1.setId(1235L);
		or1.setQuantity(15L);

		Orders or2 = new Orders();
		or2.setId(1237L);
		or2.setQuantity(15L);
		List<Orders> list = Arrays.asList(or, or1, or2);
		return new PageImpl<>(list);
	}

}