package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.gateways.ItemGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class ItemService {
	private final ItemGateway itemGateway;

	public ItemService(ItemGateway itemGateway) {
		this.itemGateway = itemGateway;
	}

	public Item create(Item item) {
		return itemGateway.save(item);
	}

	public Item findById(Long id) {
		return itemGateway.findById(id);
	}

	public void update(Long id, Item item) {
		Item i = findById(id);
		item.setId(i.getId());
		itemGateway.update(item);
	}

	public void delete(Long id) {
		itemGateway.deleteById(id);
	}

	public List<Item> findAll(Sort sort) {
		return itemGateway.findAll(sort);
	}

	public Page<Item> findAll(Pageable pageable) {
		return itemGateway.findAll(pageable);
	}
}
