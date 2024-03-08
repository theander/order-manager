package com.anderson.ordermanager.controller;

import com.anderson.ordermanager.controller.dto.ItemDto;
import com.anderson.ordermanager.entity.Item;
import com.anderson.ordermanager.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("item")
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        itemService.create(itemDto);
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    @GetMapping("item/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.findById(Long.parseLong(id)), HttpStatus.OK);
    }
    @GetMapping("item")
    public ResponseEntity<List<Item>> findAll() {
        List<Item> itemList = itemService.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PutMapping("item/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable(value = "id") Long id, @RequestBody ItemDto itemDto) {
        itemService.update(id, itemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("item/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable(value = "id") Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
