package org.example.car.service;

import org.example.car.entity.Category;
import org.example.car.repository.CategoryRepository;
import org.example.car.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryDescription(categoryRequest.getCategoryDescription());
        if (categoryRepository.findCategoryByCategoryName(categoryRequest.getCategoryName()).isPresent()) {
            throw new RuntimeException("Loại xe đã tồn tại");
        }
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Loại xe không tồn tại"));
    }

    public Category updateCategoryById(Integer categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Loại xe không tồn tại"));

        categoryRepository.findCategoryByCategoryName(categoryRequest.getCategoryName()).ifPresent(existing -> {
            // So sánh bằng != nếu là int, hoặc .equals nếu là Integer
            if (existing.getCategoryId() != categoryId) {
                throw new RuntimeException("Tên loại xe đã tồn tại");
            }
        });

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryDescription(categoryRequest.getCategoryDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
