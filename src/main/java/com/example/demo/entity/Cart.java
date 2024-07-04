package com.example.demo.entity;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "cart")
public class Cart {

  private @Id
  @GeneratedValue Long id;
  private String customer;
  private String address;
  private int total;
  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CartProduct> cartProducts;

  Cart() {

  }

  public Cart(String customer, String address, int total) {
    this.customer = customer;
    this.address = address;
    this.total = total;
  }

  public Long getId() {
    return this.id;
  }

  public String getCustomer() {
    return this.customer;
  }

  public String getAddress() {
    return this.address;
  }

  public int getTotal() {
    return this.total;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setTotal(int total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Cart))
      return false;
    Cart cart = (Cart) o;
    return Objects.equals(this.id, cart.id) && Objects.equals(this.customer, cart.customer)
        && Objects.equals(this.address, cart.address)  && Objects.equals(this.total, cart.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.customer, this.address, this.total);
  }

  @Override
  public String toString() {
    return "Product{" + "id=" + this.id + ", customer='" + this.customer + '\'' + ", address='" + this.address + '\'' + ", total='" + this.total + '\'' +'}';
  }
}