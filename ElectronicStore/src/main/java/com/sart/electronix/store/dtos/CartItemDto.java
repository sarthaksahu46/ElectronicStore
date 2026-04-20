package com.sart.electronix.store.dtos;

import com.sart.electronix.store.entities.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemsId;
    private Product product;
    private int quantity;
    private int totalPrice;

}
