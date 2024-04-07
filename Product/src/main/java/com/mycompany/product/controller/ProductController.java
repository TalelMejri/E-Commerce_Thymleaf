package com.mycompany.product.controller;

import com.mycompany.product.model.Product;
import com.mycompany.product.service.CategoryService;
import com.mycompany.product.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/List_Products")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "Products/List_Products";
    }


    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // Récupérer toutes les catégories
        return "Products/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/Products/List_Products"; // Redirection vers la liste des produits
    }


    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Récupérer toutes les catégories
        if(product != null) {
            model.addAttribute("product", product);
            return "Products/editProduct";
        } else {
            // Gérer le cas où la catégorie n'est pas trouvée
            return "redirect:/Products/List_Products";
        }

    }

    @PostMapping("/editProduct/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        productService.updateProduct(id, product);
        return "redirect:/Products/List_Products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String showDeleteProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "Products/deleteProduct";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/Products/List_Products";
    }

    @GetMapping("/personal")
    public String returnToMainMenu() {
        return "personal_page";
    }
}
