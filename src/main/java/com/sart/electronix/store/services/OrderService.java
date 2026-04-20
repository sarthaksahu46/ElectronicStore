package com.sart.electronix.store.services;

import com.sart.electronix.store.dtos.CreateOrderRequest;
import com.sart.electronix.store.dtos.OrderDto;
import com.sart.electronix.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest request);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    //other methods related to orders

}
