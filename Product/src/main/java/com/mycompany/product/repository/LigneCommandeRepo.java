package com.mycompany.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mycompany.product.model.LigneCommande;

public interface LigneCommandeRepo extends JpaRepository<LigneCommande, Long> {

}
