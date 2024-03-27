package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.exception.custom.DeleteViolationException;
import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.app.exception.custom.UniqueConstraintViolationException;
import com.anderson.ordermanager.infra.entities.ItemEntity;
import com.anderson.ordermanager.infra.mapper.ItemMapper;
import com.anderson.ordermanager.infra.repository.ItemRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemRepositoryService {
	private final ItemRepository itemRepository;
	private final ItemMapper mapper;

	public ItemRepositoryService(ItemRepository itemRepository, ItemMapper mapper) {
		this.itemRepository = itemRepository;
		this.mapper = mapper;
	}


	public Item save(Item item) {
		try {
			return mapper.toDomain(itemRepository.save(mapper.toEntity(item)));
		} catch (
				DataIntegrityViolationException e) {
			throw new UniqueConstraintViolationException("Conflict!!!. Item already exists.");
		}
	}

	public Item findById(Long id) {
		ItemEntity item = itemRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException("Item not found"));
		return mapper.toDomain(item);
	}

	public void deleteById(Long id) {
		try {
			itemRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DeleteViolationException("Delete not allowed due to entity association");
		}
	}

	public void update(Item item) {
		try {
			itemRepository.save(mapper.toEntity(item));
		} catch (DataIntegrityViolationException e) {
			throw new DeleteViolationException("Delete not allowed due to entity association");
		}
	}

	public List<Item> findAll(Sort sort) {
		List<ItemEntity> all = itemRepository.findAll(sort);
		return all.stream().map(mapper::toDomain).toList();
	}

	public Page<Item> findAll(Pageable pageable) {
		Page<ItemEntity> all = itemRepository.findAll(pageable);
		return all.map(mapper::toDomain);
	}
}
