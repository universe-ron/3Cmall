package com.willy.malltest.service;

import com.willy.malltest.dto.CartDto;
import com.willy.malltest.model.CartItems;
import com.willy.malltest.model.User;

import java.util.List;

public interface CartService {
    public List<CartDto> findCartByUserId(Long userId);
    public boolean deleteCartItem(Integer cartItemId);
    public CartItems addToCart(Long userId, String specId, int quantity);
    public void updateCartItemQuantity(CartDto cartDto);
    public void clearCartByUserId(Long userId);
}
