package com.su.ateliershop.entity;

import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long goodId;
    private Integer count;
    private Double price;
}
