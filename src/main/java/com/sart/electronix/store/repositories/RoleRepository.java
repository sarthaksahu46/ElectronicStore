package com.sart.electronix.store.repositories;

import com.sart.electronix.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
