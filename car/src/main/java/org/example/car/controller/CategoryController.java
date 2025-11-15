package org.example.car.controller;

import org.example.car.entity.Category;
import org.example.car.request.CategoryRequest;
import org.example.car.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/get")
    public List<Category> getCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/get/{categoryId}")
    public Category getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @PutMapping("/update/{categoryId}")
    Category updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategoryById(categoryId, categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    String deleteCategoryById(@PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return "Đã xóa thành công";
    }
}
