package com.anderson.ordermanager.repository;

import com.anderson.ordermanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
