package com.anderson.ordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements = new ArrayList<>();
//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    private List<Order> order = new ArrayList<>();
}
