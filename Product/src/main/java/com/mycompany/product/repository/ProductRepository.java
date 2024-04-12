package com.mycompany.product.repository;


import com.mycompany.product.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByNameContains(String mc,Pageable pageable);

}