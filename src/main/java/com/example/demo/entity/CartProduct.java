package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cart_product")
public class CartProduct {

    @Id
    @GeneratedValue Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    int quantity;
    int total;

    public CartProduct() {

    }
  
    public CartProduct(Cart cart, Product product, int quantity, int total) {
      this.cart = cart;
      this.product = product;
      this.quantity = quantity;
      this.total = total;
    }
  
    public Long getId() {
      return this.id;
    }


    public Cart getCart() {
        return this.cart;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQty() {
        return this.quantity;
    }

    public int getTotal() {
        return this.total;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    
    // standard constructors, getters, and setters
}