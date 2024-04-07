package com.mycompany.product.repository;

import com.mycompany.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {
   
}