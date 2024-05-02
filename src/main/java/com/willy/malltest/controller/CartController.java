package com.willy.malltest.controller;

import com.willy.malltest.dto.AddCartDto;
import com.willy.malltest.dto.CartDto;
import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.CartItems;
import com.willy.malltest.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173","http://localhost:3000" })

public class CartController{
    @Autowired
    CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody AddCartDto addCartDto) {
        System.out.println("Received specId: " + addCartDto.getSpecId());
        System.out.println("Received quantity: " + addCartDto.getQuantity());
        CartItems addToCart = cartService.addToCart(addCartDto.getUserId(), addCartDto.getSpecId(), addCartDto.getQuantity());
        return ResponseEntity.ok().body(addToCart);
    }
    @GetMapping("/cart")
    public List<CartDto> getCartByUserId(HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        List<CartDto> cart = cartService.findCartByUserId(loggedInUser.getUserId());
        return cart;
    }

    @DeleteMapping("/cart/item/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId, HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in!");
        }
        try {
            boolean isDeleted = cartService.deleteCartItem(cartItemId);
            return ResponseEntity.ok().body("deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
        }
    }
    @PutMapping("/cart/{cartItemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Integer cartItemId, @RequestBody CartDto cartDto) {
        cartDto.setCartItemId(cartItemId);
        cartService.updateCartItemQuantity(cartDto);
        return ResponseEntity.ok("Cart item quantity updated successfully.");
    }
    @DeleteMapping("/cart/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCartByUserId(userId);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to clear cart: " + e.getMessage());
        }
    }
}
