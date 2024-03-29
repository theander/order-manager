package com.anderson.ordermanager.infra.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "item")
public class ItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;
	@Column(name = "name", unique = true, nullable = false)
	private String name;

}
