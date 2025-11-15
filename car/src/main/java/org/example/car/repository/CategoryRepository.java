package org.example.car.repository;

import org.example.car.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findCategoryByCategoryId(Integer categoryId);

    Optional<Category> findCategoryByCategoryName(String categoryName);
}
