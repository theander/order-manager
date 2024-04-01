package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.gateways.ItemGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
	@Mock
	ItemGateway itemGateway;
	@InjectMocks
	ItemService itemService;

	@Test
	void create() {
		Item item = getItem();
		doReturn(item).when(itemGateway).save(any());
		Item save = itemService.create(item);
		assertThat(save).isNotNull();
		verify(itemGateway).save(any());
	}

	@Test
	void findById() {
		Item item = getItem();
		doReturn(item).when(itemGateway).findById(any());
		Item finded = itemService.findById(123L);
		assertThat(finded).isNotNull();
		verify(itemGateway).findById(any());
	}

	@Test
	void update() {
		Item itemToUpdate = getItem();
		doNothing().when(itemGateway).update(any());
		doReturn(itemToUpdate).when(itemGateway).findById(any());
		itemService.update(123L, itemToUpdate);
		verify(itemGateway).update(itemToUpdate);
		verify(itemGateway).findById(123L);
	}

	@Test
	void delete() {
		doNothing().when(itemGateway).deleteById(any());
		itemService.delete(123L);
		verify(itemGateway).deleteById(123L);
	}

	@Test
	void findAll() {
		List<Item> items = getItems();
		doReturn(items).when(itemGateway).findAll((Sort) any());
		List<Item> finded = itemService.findAll(Sort.by("id"));
		assertThat(finded).isNotNull();
		assertThat(finded).hasSize(3);
		verify(itemGateway).findAll((Sort) any());
	}

	private static Item getItem() {
		Item item = new Item();
		item.setId(123L);
		item.setName("Car");
		return item;
	}

	private static List<Item> getItems() {
		Item item = new Item();
		item.setId(123L);
		item.setName("Car");
		Item item1 = new Item();
		item1.setId(1234L);
		item1.setName("Bike");
		Item item2 = new Item();
		item2.setId(1235L);
		item2.setName("Sk8");
		return Arrays.asList(item, item1, item2);
	}
}