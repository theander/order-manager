package com.anderson.ordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.util.Lazy;

import java.time.OffsetDateTime;
@Entity
@Table(name="order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private Long quantity;
}
