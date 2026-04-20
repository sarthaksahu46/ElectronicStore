package com.sart.electronix.store.repositories;

import com.sart.electronix.store.entities.Order;
import com.sart.electronix.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
