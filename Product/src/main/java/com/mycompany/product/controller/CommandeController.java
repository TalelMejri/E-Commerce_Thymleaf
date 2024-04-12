package com.mycompany.product.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.product.model.Commande;
import com.mycompany.product.model.LigneCommande;
import com.mycompany.product.model.Product;
import com.mycompany.product.repository.CommandeRepo;
import com.mycompany.product.repository.LigneCommandeRepo;
import com.mycompany.product.repository.ProductRepository;
import com.mycompany.product.service.CommandeService;

@Controller
public class CommandeController {
	 @Autowired
	 private CommandeService commandeService;
	 
	 @Autowired
	 private ProductRepository productRepository;
	 
	 @Autowired
	 private LigneCommandeRepo ligneCommandeRepo;
	 
	 @Autowired
	 private CommandeRepo commandeRepo;
	 
	 
	 @PostMapping("/cart")
	 public String passCommande(@RequestParam("cartItems") String cartItems) {
	     ObjectMapper objectMapper = new ObjectMapper();
	     try {
	        
	         List<Map<String, Object>> items = objectMapper.readValue(cartItems, new TypeReference<List<Map<String, Object>>>() {});
	         if (!items.isEmpty()) {
	         Commande commande = new Commande();
	         commande.setDateCommande(new Date());
	         commande.setItems(new ArrayList<>());
	         for (Map<String, Object> item : items) {
	             LigneCommande ligneCommande = new LigneCommande();

	             Long id = Long.valueOf((Integer) item.get("id"));
	             double price = ((Number) item.get("price")).doubleValue();
	             int quantity =  (int) item.get("quantity");

	             // Retrieve product from repository based on id
	             Product product = productRepository.getById(id);

	             if (product != null) {
	                 ligneCommande.setProduit(product);
	                 ligneCommande.setPrix(price);
	                 ligneCommande.setQuantite(quantity);
	                 ligneCommande.setCommande(commande);
	                 commande.getItems().add(ligneCommande);
	                 
	                 
	             }
	             commandeRepo.save(commande);
	             ligneCommandeRepo.save(ligneCommande);
	         }
	         }
	     } catch (JsonMappingException e) {
	         e.printStackTrace();
	     } catch (JsonProcessingException e) {
	         e.printStackTrace();
	     }

	     return "redirect:/cart";
	 }


}
