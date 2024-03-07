package com.anderson.ordermanager.repository;


import com.anderson.ordermanager.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
