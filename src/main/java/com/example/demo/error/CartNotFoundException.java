package com.example.demo.error;

public class CartNotFoundException extends RuntimeException {

  public CartNotFoundException(Long id) {
    super("Could not find Cart with id:" + id);
  }
}