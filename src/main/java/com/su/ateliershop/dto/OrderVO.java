package com.su.ateliershop.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class OrderVO {
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
    private List<OrderItemVO> items;
    private Map<String, String> logistics;

    @Data
    public static class OrderItemVO {
        private Long goodId;
        private Integer count;
        private Double price;
    }
}
