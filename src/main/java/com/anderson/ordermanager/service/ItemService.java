package com.anderson.ordermanager.service;

import com.anderson.ordermanager.controller.dto.ItemDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
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
        itemRepository.save(item);
    }

    public Item findById(Long id){
        Optional<Item> byId = itemRepository.findById(id);
        if(byId.isEmpty()){
            throw new EntityNotFoundException("Item not found");
        }
        return byId.get();
    }
    public void update(Long id, ItemDto itemDto) {
        Item item = findById(id);
        item.setName(itemDto.getName());
        itemRepository.save(item);
    }
    public void delete(Long id){
        itemRepository.deleteById(id);
    }

    public List<Item> findAll(Sort sort) {
        return itemRepository.findAll(sort);
    }
}
