package com.su.ateliershop.entity;

import lombok.Data;

@Data
public class Carousel {
    private Long id;
    private String title;
    private String subtitle;
    private String img;
    private String link;
    private Integer sortOrder;
}
