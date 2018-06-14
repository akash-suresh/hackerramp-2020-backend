package com.myntra.critico.controller;

import com.myntra.critico.exception.ResourceNotFoundException;
import com.myntra.critico.model.Product;
import com.myntra.critico.model.ProductResponse;
import com.myntra.critico.model.Review;
import com.myntra.critico.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ProductResponse getProductById(@PathVariable(value = "id") Long productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        ProductResponse productResponse = new ProductResponse();
        if(Objects.nonNull(product)) {
            productResponse.setProductName(product.getProductName());
            productResponse.setProductDescription(product.getProductDescription());
            productResponse.setProductLargeImgURL(product.getProductLargeImgURL());

            List<Review> socialReviews= new ArrayList<>();
            List<Review> videoReviews= new ArrayList<>();
            List<Review> userReviews= new ArrayList<>();
            for(Review review : product.getReviews()){
                if("youtube".equals(review.getSourceName())){
                    videoReviews.add(review);
                }
                else if("instagram".equals(review.getSourceName())){
                    socialReviews.add(review);
                }
                else if("flipkart".equals(review.getSourceName()) || "amazon".equals(review.getSourceName())) {
                    userReviews.add(review);
                }
            }
            productResponse.setSocialReviews(socialReviews);
            productResponse.setVideoReviews(videoReviews);
            productResponse.setUserReviews(userReviews);
        }

        return productResponse;
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
