package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.service.ItemService;
import com.anderson.ordermanager.infra.mapper.ItemMapper;
import com.anderson.ordermanager.infra.web.dto.ItemDto;
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

@WebMvcTest(ItemController.class)
@ActiveProfiles("test")
class ItemControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	ItemService itemService;
	@MockBean
	ItemMapper mapper;

	@Test
	void createItem() throws Exception {
		ItemDto itemDto = this.createItemDto();
		Item item = this.createItemEntity();
		String content = objectMapper.writeValueAsString(itemDto);
		when(itemService.create(any())).thenReturn(item);
		mockMvc.perform(post("/api/item")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andReturn();
		verify(itemService, times(1)).create(any());
	}

	@Test
	void findItemById() throws Exception {
		Item item = this.createItemEntity();
		when(itemService.findById(any())).thenReturn(eq(item));
		mockMvc.perform(get("/api/item/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(itemService, times(1)).findById(123L);
	}


	@Test
	void findAll() throws Exception {
		Page<Item> page = getItems();
		when(itemService.findAll((Pageable) any())).thenReturn(page);
		mockMvc.perform(get("/api/item")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pagination.quantity").value(3l))
				.andExpect(jsonPath("$.pagination.next").value(nullValue()))
				.andExpect(jsonPath("$.pagination.prev").value(nullValue()))
				.andExpect(jsonPath("$.items.[1].id").value(2l))
				.andExpect(jsonPath("$.items.[1].name").value("Barco"))
				.andReturn();
	}

	@Test
	void updateItem() throws Exception {
		Item item = this.createItemEntity();
		String content = objectMapper.writeValueAsString(item);
		item.setName("Bike");
		doNothing().when(itemService).update(any(), eq(item));
		mockMvc.perform(put("/api/item/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content)
				)
				.andExpect(status().isOk())
				.andReturn();
		verify(itemService, times(1)).update(any(), any());
	}

	@Test
	void deleteItem() throws Exception {
		doNothing().when(itemService).delete(any());
		mockMvc.perform(delete("/api/item/{id}", 123L)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)

				)
				.andExpect(status().isNoContent())
				.andReturn();
		verify(itemService, times(1)).delete(123L);
	}

	private ItemDto createItemDto() {
		ItemDto itemDto = new ItemDto();
		itemDto.setName("Car");
		return itemDto;
	}

	private Item createItemEntity() {
		Item item = new Item();
		item.setId(123L);
		item.setName("Car");
		return item;
	}

	private static Page<Item> getItems() {
		Item item1 = new Item();
		item1.setId(1L);
		item1.setName("Carro");
		Item item2 = new Item();
		item2.setId(2L);
		item2.setName("Barco");
		Item item3 = new Item();
		item3.setId(3L);
		item3.setName("Bike");
		List<Item> items = Arrays.asList(item1, item2, item3);
		return new PageImpl<>(items);
	}
}