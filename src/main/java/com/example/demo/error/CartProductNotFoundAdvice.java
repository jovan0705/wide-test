package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CartProductNotFoundAdvice {

  @ExceptionHandler(CartProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String cartProductNotFoundHandler(CartProductNotFoundException ex) {
    return ex.getMessage();
  }
}