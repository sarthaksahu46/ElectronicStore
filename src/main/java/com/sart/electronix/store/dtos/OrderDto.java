package com.sart.electronix.store.dtos;

import com.sart.electronix.store.entities.OrderStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private String orderId;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private boolean paymentStatus = false;
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate = new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();

}
