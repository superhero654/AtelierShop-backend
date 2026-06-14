package com.su.ateliershop.entity;

import lombok.Data;

@Data
public class Good {
    private Long id;
    private String name;
    private Double price;
    private Long categoryId;
    private String img;
    private String description;
    private Integer stock;
    private String status;
    private Boolean hot;
}
