package com.sart.electronix.store.repositories;

import com.sart.electronix.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
