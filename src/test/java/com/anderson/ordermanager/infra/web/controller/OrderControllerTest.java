package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.StatusEnum;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.OrderService;
import com.anderson.ordermanager.infra.mapper.OrderMapper;
import com.anderson.ordermanager.infra.web.dto.OrderDto;
import com.anderson.ordermanager.infra.web.pagination.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	OrderService orderService;
	@MockBean
	BusinessService businessService;
	@MockBean
	Pagination pagination;
	@MockBean
	OrderMapper mapper;

	@Test
	void createOrder() throws Exception {
		OrderDto orderDto = this.createOrderDto();
		Orders order = this.createOrderEntity();
		String content = objectMapper.writeValueAsString(orderDto);
		when(orderService.create(any())).thenReturn(order);
		mockMvc.perform(post("/api/order")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		verify(orderService, times(1)).create(any());
	}

	@Test
	void findOrderById() throws Exception {
		Orders orderEntity = this.createOrderEntity();
		when(orderService.findById(any())).thenReturn(eq(orderEntity));
		mockMvc.perform(get("/api/order/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(orderService, times(1)).findById(123L);
	}

	@Test
	void findAll() {
	}

	@Test
	void updateOrder() throws Exception {
		Orders orderEntity = this.createOrderEntity();
		OrderDto orderDto = this.createOrderDto();
		String content = objectMapper.writeValueAsString(orderDto);

		doReturn(orderEntity).when(orderService).update(any(), eq(orderEntity));
		mockMvc.perform(put("/api/order/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(orderService, times(1)).update(any(), any());
	}

	@Test
	void deleteOrder() throws Exception {
		doNothing().when(orderService).delete(any());
		mockMvc.perform(delete("/api/order/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		verify(orderService, times(1)).delete(123L);
	}

	private Orders createOrderEntity() {
		Orders order = new Orders();
		Users users = new Users();
		users.setName("john");
		users.setEmail("john@gmail.com");
		users.setId(15L);
		Item item = new Item();
		item.setName("Car");
		item.setId(1515L);
		order.setUser(users);
		order.setItem(item);
		order.setStatus(StatusEnum.CREATED);
		order.setQuantity(10L);
		order.setId(12L);
		return order;
	}

	private OrderDto createOrderDto() {
		OrderDto orderDto = new OrderDto();
		orderDto.setItemId(123L);
		orderDto.setQuantity(10L);
		orderDto.setUserId(15L);
		return orderDto;
	}
}