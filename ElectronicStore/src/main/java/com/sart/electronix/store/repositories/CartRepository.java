package com.sart.electronix.store.repositories;

import com.sart.electronix.store.entities.Cart;
import com.sart.electronix.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
