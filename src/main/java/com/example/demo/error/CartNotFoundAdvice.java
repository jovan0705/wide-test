package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CartNotFoundAdvice {

  @ExceptionHandler(CartNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String cartNotFoundHandler(CartNotFoundException ex) {
    return ex.getMessage();
  }
}