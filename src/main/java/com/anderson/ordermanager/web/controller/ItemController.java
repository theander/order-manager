package com.anderson.ordermanager.web.controller;

import com.anderson.ordermanager.web.dto.ItemDto;
import com.anderson.ordermanager.web.dto.SortEnum;
import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.service.ItemService;
import com.anderson.ordermanager.web.pagination.Pagination;
import com.anderson.ordermanager.web.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
	public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name")  String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) SortEnum sortDirection) {

        Pagination pagination = new Pagination();
        Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection.getValue());
        Page<Item> itemsPage = itemService.findAll(pageable);
        Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection.getValue(), itemsPage);
        PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);
		return ResponseEntity.ok(response);
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

	private Sort sorter(SortEnum sort) {
		if (sort.name() == null || sort.name().equals(SortEnum.ASC.name())) {
			return Sort.by(Sort.Direction.ASC, "name");
		} else {
			return Sort.by(Sort.Direction.DESC, "name");
		}
	}

	@GetMapping("/api/test")
	public ResponseEntity<?> getItems(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {
		Pagination pagination = new Pagination();

        Pageable pageable = pagination.createPageable(page, size, sortBy, sortDirection);
		Page<Item> itemsPage = itemService.findAll(pageable);

		Pagination pagination1 = pagination.createPagination(page, size, sortBy, sortDirection, itemsPage);
		PaginationResponse response = new PaginationResponse(itemsPage.getContent(), pagination1);

		return ResponseEntity.ok(response);
	}


}
