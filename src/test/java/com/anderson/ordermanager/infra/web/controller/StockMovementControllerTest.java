package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.service.BusinessService;
import com.anderson.ordermanager.app.service.StockMovementService;
import com.anderson.ordermanager.infra.mapper.StockMovementMapper;
import com.anderson.ordermanager.infra.web.dto.StockMovementDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockMovementController.class)
@ActiveProfiles("test")
class StockMovementControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	StockMovementService stockMovementService;
	@MockBean
	BusinessService businessService;
	@MockBean
	StockMovementMapper mapper;

	@Test
	void createStockMovement() throws Exception {
		StockMovementDto smDto = this.createStockMovementDto();
		StockMovement stockMovementMock = this.createStockMovementMock();
		String content = objectMapper.writeValueAsString(smDto);
		when(stockMovementService.create(any())).thenReturn(stockMovementMock);
		mockMvc.perform(post("/api/stock-movement")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.quantity").exists())
				.andReturn();
		verify(stockMovementService, times(1)).create(any());
	}

	private StockMovementDto createStockMovementDto() {
		StockMovementDto stockMovementDto = new StockMovementDto();
		stockMovementDto.setItemId(3L);
		stockMovementDto.setQuantity(10L);
		return stockMovementDto;
	}

	@Test
	void findStockMovementById() throws Exception {
		StockMovement sm = this.createStockMovementMock();
		when(stockMovementService.findById(any())).thenReturn(eq(sm));
		mockMvc.perform(get("/api/stock-movement/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(stockMovementService, times(1)).findById(123L);
	}


	@Test
	void findAll() throws Exception {
		Page<StockMovement> page = getStockMovements();
		when(stockMovementService.findAll((Pageable) any())).thenReturn(page);
		mockMvc.perform(get("/api/stock-movement")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pagination.quantity").value(3l))
				.andExpect(jsonPath("$.pagination.next").value(nullValue()))
				.andExpect(jsonPath("$.pagination.prev").value(nullValue()))
				.andExpect(jsonPath("$.items.[1].id").value(2l))
				.andReturn();
	}

	@Test
	void updateStockMovement() throws Exception {
		StockMovement stockMovementMock = this.createStockMovementMock();
		String content = objectMapper.writeValueAsString(stockMovementMock);
		doNothing().when(stockMovementService).update(any(), eq(stockMovementMock));
		mockMvc.perform(put("/api/stock-movement/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(status().isOk())
				.andReturn();
		verify(stockMovementService, times(1)).update(any(), any());

	}

	@Test
	void deleteStockMovement() throws Exception {
		doNothing().when(stockMovementService).delete(any());
		mockMvc.perform(delete("/api/stock-movement/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		verify(stockMovementService, times(1)).delete(123L);
	}

	private StockMovement createStockMovementMock() {
		Item item = new Item();
		item.setId(5L);
		item.setName("Car");

		StockMovement stockMovement = new StockMovement();
		stockMovement.setItem(item);
		stockMovement.setQuantity(10L);
		stockMovement.setId(12L);
		return stockMovement;
	}

	private static Page<StockMovement> getStockMovements() {
		StockMovement sm1 = new StockMovement();
		sm1.setId(1L);
		sm1.setQuantity(10L);
		StockMovement sm2 = new StockMovement();
		sm2.setId(2L);
		sm2.setQuantity(20L);
		StockMovement sm3 = new StockMovement();
		sm3.setId(3L);
		sm3.setQuantity(30L);
		List<StockMovement> sms = Arrays.asList(sm1, sm2, sm3);
		return new PageImpl<>(sms);

	}
}