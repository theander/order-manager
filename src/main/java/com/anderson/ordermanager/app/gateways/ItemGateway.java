package com.anderson.ordermanager.app.gateways;

import com.anderson.ordermanager.app.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemGateway {
	Item save(Item item);

	Item findById(Long id);

	void update(Item item);

	void deleteById(Long id);

	List<Item> findAll(Sort sort);
	Page<Item> findAll(Pageable pageable);
}
