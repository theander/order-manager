package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.ItemDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.exception.custom.DeleteViolationException;
import com.anderson.ordermanager.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.exception.custom.UniqueConstraintViolationException;
import com.anderson.ordermanager.repository.ItemRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void create(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        try {
            itemRepository.save(item);
        } catch (
                DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Conflict!!!. Item already exists.");
        }
    }

    public Item findById(Long id) {
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Item not found");
        }
        return byId.get();
    }

    public void update(Long id, ItemDto itemDto) {
        Item item = findById(id);
        item.setName(itemDto.getName());
        itemRepository.save(item);
    }

    public void delete(Long id) {
        try {
            itemRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DeleteViolationException("Delete not allowed due to entity association");
        }
    }

    public List<Item> findAll(Sort sort) {
        return itemRepository.findAll(sort);
    }
}
