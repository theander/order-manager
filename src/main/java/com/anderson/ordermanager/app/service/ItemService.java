package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.infra.service.ItemRepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class ItemService {
	private final ItemRepositoryService itemRepositoryService;

	public ItemService(ItemRepositoryService itemRepositoryService) {
		this.itemRepositoryService = itemRepositoryService;
	}

	public Item create(Item item) {
		return itemRepositoryService.save(item);
	}

	public Item findById(Long id) {
		return itemRepositoryService.findById(id);
	}

	public void update(Long id, Item item) {
		Item i = findById(id);
		item.setId(i.getId());
		itemRepositoryService.update(item);
	}

	public void delete(Long id) {
		itemRepositoryService.deleteById(id);
	}

	public List<Item> findAll(Sort sort) {
		return itemRepositoryService.findAll(sort);
	}

	public Page<Item> findAll(Pageable pageable) {
		return itemRepositoryService.findAll(pageable);
	}
}
