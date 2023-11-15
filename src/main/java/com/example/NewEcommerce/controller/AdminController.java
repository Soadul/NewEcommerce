package com.example.NewEcommerce.controller;

import com.example.NewEcommerce.Service.CategoryService;
import com.example.NewEcommerce.Service.ProductService;
import com.example.NewEcommerce.dto.ProductDTO;
import com.example.NewEcommerce.model.Category;
import com.example.NewEcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
  public static String uploadDir =
      System.getProperty("user.dir") + "/src/main/resources/static/productImages";

  @Autowired CategoryService categoryService;

  @Autowired ProductService productService;

  @GetMapping("/admin")
  public String adminHome() {
    return "adminHome";
  }

  @GetMapping("/admin/categories")
  public String getCat(Model model) {
    model.addAttribute("categories", categoryService.getAllCategory());
    return "categories";
  }

  @GetMapping("/admin/categories/add")
  public String getCatAdd(Model model) {
    model.addAttribute("category1", new Category());
    return "categoriesAdd";
  }

  @PostMapping("/admin/categories/add")
  public String postCatAdd(@ModelAttribute("category1") Category category) {
    categoryService.addCategory(category);
    return "redirect:/admin/categories";
  }

  @GetMapping("/admin/categories/update/{id}")
  public String updateCat(@PathVariable int id, Model model) {
    Optional<Category> categoryOptional = categoryService.getCategoryById(id);
    if (!categoryOptional.isEmpty()) {
      Category category = categoryOptional.get();
      model.addAttribute("category1", category);
      return "categoriesAdd";
    } else return "404";
  }

  @GetMapping("/admin/categories/delete/{id}")
  public String deleteCat(@PathVariable int id) {
    categoryService.removeCategoryById(id);
    return "redirect:/admin/categories";
  }

  @GetMapping("/admin/products")
  public String getProduct(Model model) {
    model.addAttribute("products", productService.getAllProduct());
    return "products";
  }

  @GetMapping("/admin/products/add")
  public String addProduct(Model model) {
    model.addAttribute("productDTO", new ProductDTO());
    model.addAttribute("categories", categoryService.getAllCategory());
    return "productsAdd";
  }

  @PostMapping("/admin/products/add")
  public String productAddPost(
      @ModelAttribute("productDTO") ProductDTO productDTO,
      @RequestParam("productImage") MultipartFile file,
      @RequestParam("imgName") String imgName)
      throws IOException {
    Product product = new Product();
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
    product.setPrice(productDTO.getPrice());
    product.setWeight(productDTO.getWeight());
    product.setDescription(productDTO.getDescription());

    String imageUUID;
    if (!file.isEmpty()) {
      imageUUID = file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
      Files.write(fileNameAndPath, file.getBytes());
    } else imageUUID = imgName;
    product.setImageName(imageUUID);
    productService.addProduct(product);
    return "redirect:/admin/products";
  }

  @GetMapping("/admin/product/update/{id}")
  public String updateProduct(@PathVariable long id, Model model) {
    Optional<Product> productOptional = productService.getProductById(id);
    if (!productOptional.isEmpty()) {
      Product product = productService.getProductById(id).get();
      ProductDTO productDTO = new ProductDTO();
      productDTO.setId(product.getId());
      productDTO.setName(product.getName());
      productDTO.setCategoryId(product.getCategory().getId());
      productDTO.setPrice(product.getPrice());
      productDTO.setWeight(product.getWeight());
      productDTO.setDescription(product.getDescription());
      productDTO.setImageName(product.getImageName());
      model.addAttribute("categories", categoryService.getAllCategory());
      model.addAttribute("productDTO", productDTO);

      model.addAttribute("categories", categoryService.getAllCategory());
      model.addAttribute("productDTO", productDTO);
      return "productsAdd";
    } else return "404";
  }

  @GetMapping("/admin/product/delete/{id}")
  public String deleteProduct(@PathVariable long id) {
    productService.removeProductById(id);
    return "redirect:/admin/products";
  }
}
