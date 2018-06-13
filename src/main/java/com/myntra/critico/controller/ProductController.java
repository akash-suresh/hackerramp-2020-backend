package com.myntra.critico.controller;

import com.myntra.critico.exception.ResourceNotFoundException;
import com.myntra.critico.model.Product;
import com.myntra.critico.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    /*
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value = "id") Long productId,
                                           @Valid @RequestBody Product newproductDetail) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        //set all attributes
        product.setProductName(newproductDetail.getProductName());

        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        productRepository.delete(product);

        return ResponseEntity.ok().build();
    }
    */
}
