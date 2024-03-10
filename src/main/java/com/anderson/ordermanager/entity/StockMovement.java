package com.anderson.ordermanager.entity;

import com.anderson.ordermanager.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "stock_movement")
@Data
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column(name = "status")
    private StatusEnum status;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "creation_date")
    private Date creationDate;
    @PrePersist
    private void setCreationDate(){
        this.creationDate = new Date();
    }
}
