package com.su.ateliershop.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShopOrder {
    private Long id;
    private Long userId;
    private String orderNo;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private Integer status;
    private Double totalPrice;
    private String address;
    private String receiver;
    private String phone;
    private String logisticsCompany;
    private String trackingNo;
    private String logisticsStatus;
}
