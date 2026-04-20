package com.sart.electronix.store.services.impl;

import com.sart.electronix.store.dtos.CreateOrderRequest;
import com.sart.electronix.store.dtos.OrderDto;
import com.sart.electronix.store.dtos.PageableResponse;
import com.sart.electronix.store.entities.*;
import com.sart.electronix.store.exceptions.BadApiRequestException;
import com.sart.electronix.store.exceptions.ResourceNotFoundException;
import com.sart.electronix.store.helper.Helper;
import com.sart.electronix.store.repositories.CartRepository;
import com.sart.electronix.store.repositories.OrderRepository;
import com.sart.electronix.store.repositories.UserRepository;
import com.sart.electronix.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {

        String cartId = request.getCartId();
        String userId = request.getUserId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID not found!"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with given ID not found"));

        List<CartItem> cartItems = cart.getItems();

        if(cartItems.size() <= 0)
            throw new BadApiRequestException("Invalid number of items in cart");

        //generate order
        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .orderStatus(request.getOrderStatus())
                .paymentStatus(request.isPaymentStatus())
                .billingAddress(request.getBillingAddress())
                .billingPhone(request.getBillingPhone())
                .billingName(request.getBillingName())
                .orderedDate(new Date())
                .deliveredDate(null)
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            //CartItem -> OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order with given ID not found"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID not found"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);

    }
}
