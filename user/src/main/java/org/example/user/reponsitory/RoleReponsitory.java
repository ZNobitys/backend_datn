package org.example.user.reponsitory;

import org.example.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleReponsitory extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
