package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.error.CartNotFoundException;
import com.example.demo.error.ProductNotFoundException;
import com.example.demo.repository.CartProductRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

@RestController
class ProductController {

  private final ProductRepository repository;
  private final CartProductRepository cartProductRepository;
  private final CartRepository cartRepository;

  ProductController(ProductRepository repository, CartProductRepository cartProductRepository, CartRepository cartRepository) {
    this.repository = repository;
    this.cartProductRepository = cartProductRepository;
    this.cartRepository = cartRepository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/products")
  List<Product> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/products")
  Product newProduct(@RequestBody Product newProduct) {
    return repository.save(newProduct);
  }

  // Single item
  
  @GetMapping("/products/{id}")
  Product one(@PathVariable Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @PutMapping("/products/{id}")
  Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(product -> {
        if(newProduct.getName() != null) {
          product.setName(newProduct.getName());
        }
        if(newProduct.getType() != null) {
          product.setType(newProduct.getType());
        }
        product.setPrice(newProduct.getPrice());
        return repository.save(product);
      })
      .orElseGet(() -> {
        return repository.save(newProduct);
      });
  }

  @DeleteMapping("/products/{id}")
  void deleteProduct(@PathVariable Long id) {
    repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    List<Object[]> resultList = cartProductRepository.findCartProductByProductId(id);
    Long cartId = (Long) resultList.get(0)[3];
    Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(id));
    int total = cart.getTotal();
    for (Object[] row : resultList) {
      total -= (int) row[2];
    }
    cart.setTotal(total);
    cartRepository.save(cart);
    repository.deleteById(id);
  }
}