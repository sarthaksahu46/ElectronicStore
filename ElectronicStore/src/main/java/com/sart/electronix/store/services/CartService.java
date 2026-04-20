package com.sart.electronix.store.services;

import com.sart.electronix.store.dtos.AddItemToCartRequest;
import com.sart.electronix.store.dtos.CartDto;

public interface CartService {

    //add items to cart - if cart is not available when user adds itemcart is created
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItemId);

    //remove all items from cart
    void clearCart(String userId);

    CartDto getCartOfUser(String userId);

}
