package com.example.demo.error;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super("Could not find Product with id:" + id);
  }
}