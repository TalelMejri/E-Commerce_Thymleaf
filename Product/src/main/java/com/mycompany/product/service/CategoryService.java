package com.mycompany.product.service;

import com.mycompany.product.model.Category;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getAllCategories(String mc,int page,int size);
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    Category saveCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}