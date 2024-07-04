package com.example.demo.dto;

public class CartProductDTO {

    private Long cartId;
    private Long productId;
    private int qty;

    // Constructors, getters, setters
    public CartProductDTO() {
    }

    public CartProductDTO(Long cartId, Long productId, int qty) {
        this.cartId = cartId;
        this.productId = productId;
        this.qty = qty;
    }


    public Long getCartId() {
        return this.cartId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public int getQty() {
        return this.qty;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}