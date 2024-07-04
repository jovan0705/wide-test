package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CartProductDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartProduct;
import com.example.demo.entity.Product;
import com.example.demo.error.CartNotFoundException;
import com.example.demo.error.CartProductNotFoundException;
import com.example.demo.error.ProductNotFoundException;
import com.example.demo.repository.CartProductRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

@RestController
class CartController {

  private final CartRepository repository;
  private final CartProductRepository cartProductRepository;
  private final ProductRepository productRepository;

  CartController(CartRepository repository, CartProductRepository cartProductRepository,
      ProductRepository productRepository) {
    this.repository = repository;
    this.cartProductRepository = cartProductRepository;
    this.productRepository = productRepository;
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/carts")
  List<Cart> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/carts")
  Cart newCart(@RequestBody Cart newCart) {
    newCart.setTotal(0);
    return repository.save(newCart);
  }

  @PostMapping("/carts/addItem")
  CartProduct addItemToCart(@RequestBody CartProductDTO cartProductDTO) {
    Long cartId = cartProductDTO.getCartId();
    Long productId = cartProductDTO.getProductId();
    // ini throw kalau cart g ada
    Cart cart = repository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    // ini throw kalau product ga ada
    Product selectedProduct = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
    CartProduct newCartProduct = new CartProduct();
    int total = selectedProduct.getPrice() * cartProductDTO.getQty();
    newCartProduct.setCart(cart);
    newCartProduct.setProduct(selectedProduct);
    newCartProduct.setQty(cartProductDTO.getQty());
    newCartProduct.setTotal(total);
    // belum set lagi nambah total di cartnya
    cart.setTotal(cart.getTotal() + total);
    repository.save(cart);
    return cartProductRepository.save(newCartProduct);

  }

  @GetMapping("/carts/{id}")
  Map<String, Object> allCartProducts(@PathVariable Long id) {
    List<Object[]> resultList = cartProductRepository.findCartProductDetails(id);
    Map<String, Object> cartProducts = new HashMap<>();
    if (resultList.isEmpty()) {
      Cart cart = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
      cartProducts.put("id", cart.getId());
      cartProducts.put("customer", cart.getCustomer());
      cartProducts.put("address", cart.getAddress());
      cartProducts.put("total", cart.getTotal());
      List<Map<String, Object>> products = new ArrayList<>();
      cartProducts.put("products", products);
    } else {
      List<Map<String, Object>> products = new ArrayList<>();
      cartProducts.put("id", resultList.get(0)[3]);
      cartProducts.put("customer", resultList.get(0)[6]);
      cartProducts.put("address", resultList.get(0)[7]);
      cartProducts.put("total", resultList.get(0)[5]);
      for (Object[] row : resultList) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", row[0]);
        resultMap.put("product_name", row[8]);
        resultMap.put("product_type", row[10]);
        resultMap.put("product_price", row[9]);
        resultMap.put("quantity", row[1]);
        resultMap.put("productTotal", row[2]);
        products.add(resultMap);
      }
      cartProducts.put("products", products);
    }

    return cartProducts;
  }

  @PutMapping("/carts/{id}")
  Cart replaceCart(@RequestBody Cart newCart, @PathVariable Long id) {
    return repository.findById(id)
        .map(cart -> {
          if (newCart.getCustomer() != null) {
            cart.setCustomer(newCart.getCustomer());
          }
          if (newCart.getAddress() != null) {
            cart.setAddress(newCart.getAddress());
          }
          return repository.save(cart);
        })
        .orElseThrow(() -> 
        new CartNotFoundException(id));
  }

  @PutMapping("/cartProducts/{id}")
  CartProduct replaceCartProduct(@RequestBody CartProduct newCartProduct, @PathVariable Long id) {
    return cartProductRepository.findById(id)
        .map(cartProduct -> {
          Cart cart = cartProduct.getCart();
          Product product = cartProduct.getProduct();
          int oldTotal = cartProduct.getTotal();
          int newTotal = newCartProduct.getQty() * product.getPrice();
          int total = cart.getTotal() - oldTotal + newTotal;
          cartProduct.setQty(newCartProduct.getQty());
          cartProduct.setTotal(newTotal);
          cart.setTotal(total);
          repository.save(cart);
          return cartProductRepository.save(cartProduct);
        })
        .orElseThrow(() -> 
        new CartProductNotFoundException(id));
  }

  @DeleteMapping("/carts/{id}")
  void deleteCart(@PathVariable Long id) {
    repository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    repository.deleteById(id);
  }

  @DeleteMapping("/cartProducts/{id}")
  void deleteCartItem(@PathVariable Long id) {
    CartProduct cartItem = cartProductRepository.findById(id).orElseThrow(() -> new CartProductNotFoundException(id));
    int total = cartItem.getTotal();
    Cart cart = cartItem.getCart();
    int newTotal = cart.getTotal() - total;
    cart.setTotal(newTotal);
    repository.save(cart);
    cartProductRepository.deleteById(id);
  }
}