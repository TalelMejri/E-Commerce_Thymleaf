package com.mycompany.product.service;

import com.mycompany.product.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    Category saveCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}