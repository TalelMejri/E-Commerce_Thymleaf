package com.mycompany.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mycompany.product.model.Commande;


public interface CommandeRepo  extends JpaRepository<Commande, Long> {

}
