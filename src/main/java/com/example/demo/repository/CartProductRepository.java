package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @Query(value = "SELECT cp.id, cp.quantity, cp.total as productTotal, cp.cart_id, cp.product_id, c.total, c.customer, c.address, p.name, p.price, p.type FROM cart_product cp JOIN cart c ON c.id = cp.cart_id JOIN product p ON p.id = cp.product_id WHERE c.id = :cartId", nativeQuery=true)
    List<Object[]> findCartProductDetails(Long cartId);

    @Query(value = "SELECT cp.id, cp.quantity, cp.total as productTotal, cp.cart_id, cp.product_id, c.total, c.customer, c.address, p.name, p.price, p.type FROM cart_product cp JOIN cart c ON c.id = cp.cart_id JOIN product p ON p.id = cp.product_id WHERE p.id = :productId", nativeQuery=true)
    List<Object[]> findCartProductByProductId(Long productId);
}