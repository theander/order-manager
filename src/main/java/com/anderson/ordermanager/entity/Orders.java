package com.anderson.ordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "creation_date")
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @PrePersist
    private void setCreationDate(){
        this.creationDate = new Date();
    }
}
