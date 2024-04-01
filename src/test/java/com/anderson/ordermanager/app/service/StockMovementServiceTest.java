package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.app.gateways.StockMovementGateway;
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
class StockMovementServiceTest {
	@Mock
	StockMovementGateway stockMovementGateway;
	@InjectMocks
	StockMovementService stockMovementService;

	@Test
	void create() {
		StockMovement sm = this.getStockMovement();
		doReturn(sm).when(stockMovementGateway).save(any());
		StockMovement stockMovement = stockMovementService.create(sm);
		assertThat(stockMovement).isNotNull();
		verify(stockMovementGateway).save(any());
	}

	@Test
	void findById() {
		StockMovement sm = this.getStockMovement();
		doReturn(sm).when(stockMovementGateway).findById(any());
		StockMovement byId = stockMovementService.findById(123L);
		assertThat(byId).isNotNull();
		verify(stockMovementGateway).findById(any());

	}

	@Test
	void update() {
		StockMovement itemToUpdate = this.getStockMovement();
		doNothing().when(stockMovementGateway).update(any());
		doReturn(itemToUpdate).when(stockMovementGateway).findById(any());
		stockMovementService.update(123L, itemToUpdate);
		verify(stockMovementGateway).update(itemToUpdate);
		verify(stockMovementGateway).findById(123L);
	}

	@Test
	void delete() {
		doNothing().when(stockMovementGateway).deleteById(any());
		stockMovementService.delete(123L);
		verify(stockMovementGateway).deleteById(123L);
	}

	@Test
	void findAll() {
		Page<StockMovement> stockMovements = this.getStockMovements();
		doReturn(stockMovements).when(stockMovementGateway).findAll(any());
		Pagination pagination = new Pagination();
		Pageable pageable = pagination.createPageable(0, 100, "id", "ASC");
		Page<StockMovement> all = stockMovementService.findAll(pageable);
		assertThat(all).isNotNull();
		assertThat(all).hasSize(3);
		verify(stockMovementGateway).findAll(pageable);
	}

	private Page<StockMovement> getStockMovements() {
		StockMovement sm = new StockMovement();
		sm.setId(123L);
		sm.setQuantity(10L);

		StockMovement sm1 = new StockMovement();
		sm1.setId(1235L);
		sm1.setQuantity(15L);

		StockMovement sm2 = new StockMovement();
		sm2.setId(1237L);
		sm2.setQuantity(15L);
		List<StockMovement> list = Arrays.asList(sm, sm1, sm2);
		return new PageImpl<>(list);
	}

	private StockMovement getStockMovement() {
		StockMovement sm = new StockMovement();
		sm.setId(123L);
		sm.setQuantity(10L);
		return sm;
	}

}