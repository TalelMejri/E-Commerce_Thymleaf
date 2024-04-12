package com.mycompany.product.controller;

import com.mycompany.product.model.Category;
import com.mycompany.product.model.Product;
import com.mycompany.product.repository.CategoryRepository;
import com.mycompany.product.service.CategoryService;
import com.mycompany.product.service.ProductServiceImpl;
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
@RequestMapping("/Products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/List_Products")
    public String listProducts(Model model, @RequestParam(name = "search", defaultValue = "") String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "per_page", defaultValue = "2") int size,
			@RequestParam(name = "message", defaultValue = "") String message) {

        Page<Product> products = productService.getAllProducts(mc, page, size);
        int totale = products.getTotalPages();
		int[] count_page = new int[totale];
		for (int i = 0; i < totale; i++) {
			count_page[i] = i;
		}

		model.addAttribute("pages", count_page);
		model.addAttribute("page_current", page);
		model.addAttribute("search", mc);
		model.addAttribute("size", size);
		model.addAttribute("message_succes", message);
        model.addAttribute("products", products);
        return "admin/products/ListProduct";
    }


    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll()); 
        return "admin/products/AddProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile photo, @RequestParam("category_id") long id) {
    	try {
    		if (photo.isEmpty()) {
                return "admin/products/AddProduct";
            }
    		Category cat = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not valid"));
    		product.setCategory(cat);
			product.setPhoto(photo.getOriginalFilename());
			productService.saveProduct(product);
			String path_directory = "C:\\Users\\talel\\Downloads\\Compressed\\Product\\Product\\src\\main\\resources\\static\\storage";
			Files.copy(photo.getInputStream(), Paths.get(path_directory + File.separator + photo.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("" + e);
		}
   
        return "redirect:/Products/List_Products"; 
    }


    @GetMapping("/editProduct")
    public String showEditProductForm( Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll()); 
        model.addAttribute("idcat",product.getCategory().getId());
        if(product != null) {
            model.addAttribute("product", product);
            return "admin/products/UpdateProduct";
        } else {
            return "redirect:/Products/List_Products";
        }

    }

    @PostMapping("/editProduct")
    public String updateProduct( @ModelAttribute("product") Product product,@RequestParam(name = "image") MultipartFile photo,@RequestParam("category_id") long id) {
    	int test_upload_avatar = 0;
		if (photo.getOriginalFilename() != "") {
			test_upload_avatar = 1;
		}
		try {
			Category cat = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not valid"));
    		product.setCategory(cat);
			if (test_upload_avatar == 0) {
				product.setPhoto(product.getPhoto());
				productService.saveProduct(product);
			} else {
				product.setPhoto(photo.getOriginalFilename());
				productService.saveProduct(product);

				String path_directory = "C:\\Users\\talel\\Downloads\\Compressed\\Product\\Product\\src\\main\\resources\\static\\storage";
				Files.copy(photo.getInputStream(),
						Paths.get(path_directory + File.separator + photo.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			System.out.println("error" + e.getMessage());
		}
        return "redirect:/Products/List_Products";
    }

 
    @GetMapping("/deleteProduct")
    public String deleteProduct( Long id,int page, String search, int per_page) {
        productService.deleteProduct(id);
		return "redirect:/Products/List_Products/?search=" + search +"&page="+page+"&per_page"+per_page;
    }

    @GetMapping("/personal")
    public String returnToMainMenu() {
        return "personal_page";
    }
}
