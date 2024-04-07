package com.mycompany.product.repository;

import com.mycompany.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
   
}