package com.anderson.ordermanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    @Column(name = "name", nullable = false)
    @Size(min = 3, message = "Name should contain at least 3 chars")
    private String name;
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Provide a valid e-mail address")
    private String email;


}
