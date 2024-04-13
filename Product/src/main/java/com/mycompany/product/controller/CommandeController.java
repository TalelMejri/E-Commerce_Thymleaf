package com.mycompany.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.product.model.Commande;
import com.mycompany.product.model.LigneCommande;
import com.mycompany.product.model.Product;
import com.mycompany.product.model.User;
import com.mycompany.product.repository.CommandeRepo;
import com.mycompany.product.repository.LigneCommandeRepo;
import com.mycompany.product.repository.ProductRepository;
import com.mycompany.product.repository.UserRepository;
import com.mycompany.product.service.CommandeService;
import com.mycompany.product.service.UserServiceImpl;


@Controller
@RequestMapping("/commande")
public class CommandeController {
	 @Autowired
	 private CommandeService commandeService;
	 
	 @Autowired
	 private ProductRepository productRepository;
	 
	 @Autowired
	 private LigneCommandeRepo ligneCommandeRepo;
	 
	 @Autowired
	 private CommandeRepo commandeRepo;
	 
	 @Autowired
	 private UserRepository userRepo;
	 @Autowired
	    private UserServiceImpl usereService;
	 
	 @PostMapping("/cart")
	 public String passCommande(@RequestParam("cartItems") String cartItems , HttpServletRequest request) {
	     ObjectMapper objectMapper = new ObjectMapper();
	     User user = new User();
	     try {
	    	 Cookie[] cookies = request.getCookies();
	    	 if (cookies != null) {
	    		 for (Cookie cookie : cookies) {
	    			 if (cookie.getName().equals("UserId")) {
	    				 Long user_id = Long.parseLong(cookie.getValue());
	    				 user = userRepo.getById(user_id);
	    			 }
	    		 }
	    	 }
	         List<Map<String, Object>> items = objectMapper.readValue(cartItems, new TypeReference<List<Map<String, Object>>>() {});
	         if (!items.isEmpty()) {
	         Commande commande = new Commande();
	         commande.setDateCommande(new Date());
	         commande.setItems(new ArrayList<>());
	         for (Map<String, Object> item : items) {
	             LigneCommande ligneCommande = new LigneCommande();

	             Long id = ((Integer) item.get("id")).longValue();

	             Double price = ((Integer) item.get("price")).doubleValue();

	             Integer quantity = (Integer) item.get("quantity");

	             Product product = productRepository.getById(id);

	             if (product != null && user!=null) {
	                 ligneCommande.setProduit(product);
	                 ligneCommande.setPrix(price);
	                 ligneCommande.setQuantite(quantity);
	                 ligneCommande.setCommande(commande);
	                 commande.getItems().add(ligneCommande);
	                 commande.setClient(user);
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

	 @GetMapping("/list")
	    public String listCommandes(Model model , @RequestParam(name = "status", defaultValue ="0") int status,HttpServletRequest request) {
		 try {
	    		Boolean isauth=usereService.ISAuth(request);
	        	User user=usereService.UserAuth(request);
	        	if(!isauth && user.getRole()!="Admin") {
	        		return "redirect:/";
	        	}
	    	}catch (Exception e) {
	    		return "redirect:/";
			}
		 List<Commande> commandes =commandeRepo.getCommandes(status);
		 List<Integer> statusOptions = Arrays.asList(0,1);
		    model.addAttribute("statusOptions", statusOptions);
	        model.addAttribute("commandes", commandes);
	        model.addAttribute("status", status);
	        return "admin/commande/list_commande";
	 }
	 
	 @GetMapping("/editCommande")
	 public String changeStatus(@RequestParam("id") Long id) {
		 Commande commande = new Commande();
		 commande = commandeRepo.getById(id);
		 if(commande!=null) {
			 if(commande.getStatus()==0) {
				 commande.setStatus(1);
				 commandeRepo.save(commande);
			 }else {
				 commande.setStatus(0);
				 commandeRepo.save(commande);
			 }
		 }
		 return "redirect:list";
	 }
	 
}
