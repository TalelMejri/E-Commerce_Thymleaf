package com.mycompany.product.service;

import com.mycompany.product.model.Product;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getAllProducts(String mc,int page,int size);
    Product getProductById(Long id);
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}