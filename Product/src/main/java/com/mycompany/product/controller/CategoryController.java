package com.mycompany.product.controller;


import com.mycompany.product.model.Category;
import com.mycompany.product.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list_category")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/ListCat";
    }

    @GetMapping("/add_category")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add_category";
    }

    @PostMapping("/add_category")
    public String categories(@ModelAttribute("category") Category category, @RequestParam("photo") MultipartFile photo) {
    	try {
			if (photo.getOriginalFilename() == "") {
				return "categories/add_category";
			}
			category.setNomPhoto(photo.getOriginalFilename());
			categoryService.createCategory(category);
			String path_directory = "C:\\Users\\talel\\Downloads\\Compressed\\Product\\Product\\src\\main\\resources\\static\\storage";
			Files.copy(photo.getInputStream(), Paths.get(path_directory + File.separator + photo.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("" + e);
		}
    	
        return "redirect:/categories/list_category";
    }

    @GetMapping("/edit_category/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if(category != null) {
            model.addAttribute("category", category);
            return "categories/edit_category";
        } else {
            // Gérer le cas où la catégorie n'est pas trouvée
            return "redirect:/categories/list_category";
        }
    }

    @PostMapping("/edit_category/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories/list_category";
    }

    @GetMapping("/delete_category/{id}")
    public String showDeleteCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return "redirect:/categories/list_category";
    }

  
    @GetMapping("/personal")
    public String returnToMainMenu() {
        return "personal_page";
    }
}
