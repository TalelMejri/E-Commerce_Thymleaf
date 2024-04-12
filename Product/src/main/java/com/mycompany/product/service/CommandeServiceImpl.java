package com.mycompany.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.product.model.Commande;
import com.mycompany.product.repository.CommandeRepo;


@Service
public class CommandeServiceImpl implements CommandeService {

	@Autowired
	public CommandeRepo commandeRepo;
	
	@Override
	public Commande createCommande(Commande commande) {
		return commandeRepo.save(commande);
	}
	
}
