package org.example.components.reponsitory;

import org.example.components.entity.Components;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComponentsReponsitory extends JpaRepository<Components, Integer> {
    Optional<Components> findByComponentsName(String componentsName);
    Optional<Components> findByComponentsId(Integer componentsId);
}
