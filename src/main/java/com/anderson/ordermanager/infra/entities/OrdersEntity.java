package com.anderson.ordermanager.infra.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    private UsersEntity user;
    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private ItemEntity item;
    @Column(name = "status",nullable = false)
    private StatusEnum status;
    @Column(name = "quantity",nullable = false)
    @Min(value = 1, message = "Minimum value allowed is 1")
    private Long quantity;
    @Column(name = "creation_date")
    private Date creationDate;
    @PrePersist
    private void setCreationDate(){
        this.creationDate = new Date();
    }

}
