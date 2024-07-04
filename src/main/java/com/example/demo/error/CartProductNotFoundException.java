package com.example.demo.error;

public class CartProductNotFoundException extends RuntimeException {

  public CartProductNotFoundException(Long id) {
    super("Could not find Cart Item with id:" + id);
  }
}