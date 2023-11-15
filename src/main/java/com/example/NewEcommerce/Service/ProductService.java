package com.example.NewEcommerce.Service;

import com.example.NewEcommerce.Repository.ProductRepository;
import com.example.NewEcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }
    public List<Product> getAllProductsByCategory(int id){
        return productRepository.findAllByCategory_Id(id);

    }
}
