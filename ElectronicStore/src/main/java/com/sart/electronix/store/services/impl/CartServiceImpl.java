package com.sart.electronix.store.services.impl;

import com.sart.electronix.store.dtos.AddItemToCartRequest;
import com.sart.electronix.store.dtos.CartDto;
import com.sart.electronix.store.entities.Cart;
import com.sart.electronix.store.entities.CartItem;
import com.sart.electronix.store.entities.Product;
import com.sart.electronix.store.entities.User;
import com.sart.electronix.store.exceptions.BadApiRequestException;
import com.sart.electronix.store.exceptions.ResourceNotFoundException;
import com.sart.electronix.store.repositories.CartItemRepository;
import com.sart.electronix.store.repositories.CartRepository;
import com.sart.electronix.store.repositories.ProductRepository;
import com.sart.electronix.store.repositories.UserRepository;
import com.sart.electronix.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity <= 0)
            throw new BadApiRequestException("Requested quantity is not valid!");

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database"));
        //fetch the user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database"));

        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString()  );
            cart.setCreatedAt(new Date());
        }

        //perform cart operations
        //boolean cartItemExists = false;
        AtomicReference<Boolean> cartItemExists = new AtomicReference<>(false);
        //if cart item already exists, then update
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item-> {
            if(item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getPrice());
                cartItemExists.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        //create items
        if(!cartItemExists.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);

    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem not foudn in db"));
        cartItemRepository.delete(cartItem);

    }

    @Override
    public void clearCart(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
        return mapper.map(cart, CartDto.class);
    }
}
