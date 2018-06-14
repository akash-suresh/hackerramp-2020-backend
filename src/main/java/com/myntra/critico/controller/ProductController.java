package com.myntra.critico.controller;

import com.myntra.critico.exception.ResourceNotFoundException;
import com.myntra.critico.manager.InstagramManager;
import com.myntra.critico.model.Product;
import com.myntra.critico.model.Review;
import com.myntra.critico.repository.ProductRepository;
import com.myntra.critico.repository.ReviewRepository;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    InstagramManager instagramManager;

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

    @RequestMapping(value = "/twitter",method = RequestMethod.GET)
    public @ResponseBody void getOAuthForTwitter(@RequestParam("oauth_token") String token,@RequestParam("oauth_verifier") String verifier) {
        System.out.print("abc");
        try {
            instagramManager.processTwitterData(token,verifier);
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/twitter/oauth",method = RequestMethod.GET)
    public @ResponseBody String getTwitterDetails() {
        System.out.print("abc");
        try {
           return instagramManager.getOAuth();
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/socialreview/{id}",method = RequestMethod.GET)
    public @ResponseBody List<Review> getSocialReviews(@PathVariable(value = "id") Long productId) {

        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            return null;
        }
        try {
            List<Review> twitterReviews = instagramManager.getTwitterReviews(product.get());
            for(Review review:twitterReviews){
                try {
                    reviewRepository.save(review);
                }catch (Exception e){ }
            }
            return twitterReviews;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
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
