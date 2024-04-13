package com.mycompany.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mycompany.product.model.Commande;
import com.mycompany.product.model.User;


public interface CommandeRepo  extends JpaRepository<Commande, Long> {

	@Query(value="select * from commande where status=:status",nativeQuery=true)
	List<Commande> getCommandes(int status);
}
