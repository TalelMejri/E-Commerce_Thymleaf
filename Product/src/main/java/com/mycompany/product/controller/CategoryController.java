package com.mycompany.product.controller;


import com.mycompany.product.model.Category;
import com.mycompany.product.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String listCategories(Model model, @RequestParam(name = "search", defaultValue = "") String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "per_page", defaultValue = "2") int size,
			@RequestParam(name = "message", defaultValue = "") String message) {
    	
        Page<Category> categories = categoryService.getAllCategories(mc,page,size);
        int totale = categories.getTotalPages();
		int[] count_page = new int[totale];
		for (int i = 0; i < totale; i++) {
			count_page[i] = i;
		}

		model.addAttribute("pages", count_page);
		model.addAttribute("page_current", page);
		model.addAttribute("search", mc);
		model.addAttribute("size", size);
		model.addAttribute("message_succes", message);
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

    @GetMapping("/edit_category")
    public String showEditCategoryForm( Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if(category != null) {
            model.addAttribute("category", category);
            return "admin/Update_Category";
        } else {
            return "redirect:/categories/list_category";
        }
    }

    @PostMapping("/edit_category")
    public String updateCategory( @ModelAttribute("category") Category category,@RequestParam(name = "photo") MultipartFile photo) {
        
    	int test_upload_avatar = 0;
		if (photo.getOriginalFilename() != "") {
			test_upload_avatar = 1;
		}

		try {
			if (test_upload_avatar == 0) {
				category.setNomPhoto(category.getNomPhoto());
				categoryService.createCategory(category);
			
			} else {
				category.setNomPhoto(photo.getOriginalFilename());
				categoryService.createCategory(category);
				String path_directory = "C:\\Users\\talel\\Downloads\\Compressed\\Product\\Product\\src\\main\\resources\\static\\storage";
				Files.copy(photo.getInputStream(),
						Paths.get(path_directory + File.separator + photo.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			System.out.println("error" + e.getMessage());
		}

		String message_succes = "Update Product with succes";
		return "redirect:/categories/list_category/?message=" + message_succes;
    	
	
    }

    @GetMapping("/delete_category")
    public String showDeleteCategoryForm(Long id, int page, String search, int per_page) {
        categoryService.deleteCategory(id);
    	String  message_succes = "Delete with succes";
		return "redirect:/categories/list_category/?search=" + search +"&page="+page+"&per_page"+per_page+"&message=" + message_succes;
    }

  
    @GetMapping("/personal")
    public String returnToMainMenu() {
        return "personal_page";
    }
}
