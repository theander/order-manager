package com.anderson.ordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

}
