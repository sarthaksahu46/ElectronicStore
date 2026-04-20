package com.sart.electronix.store.dtos;

import com.sart.electronix.store.entities.OrderStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "cartId is required")
    private String cartId;
    @NotBlank(message = "userId is required")
    private String userId;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private boolean paymentStatus = false;
    @NotBlank(message = "billindAddress is required")
    private String billingAddress;
    @NotBlank(message = "billingPhone is required")
    private String billingPhone;
    @NotBlank(message = "billingName is required")
    private String billingName;

}
