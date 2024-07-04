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
@Table(name = "product")
public class Product {

  private @Id
  @GeneratedValue Long id;
  private String name;
  private String type;
  private int price;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CartProduct> cartProducts;

  Product() {

  }

  public Product(String name, String type, int price) {
    this.name = name;
    this.type = type;
    this.price = price;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public int getPrice() {
    return this.price;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPrice(int price) {
    this.price = price;
  }


  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Product))
      return false;
    Product product = (Product) o;
    return Objects.equals(this.id, product.id) && Objects.equals(this.name, product.name)
        && Objects.equals(this.type, product.type)  && Objects.equals(this.price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.type, this.price);
  }

  @Override
  public String toString() {
    return "Product{" + "id=" + this.id + ", name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", price='" + this.price + '\'' +'}';
  }
}