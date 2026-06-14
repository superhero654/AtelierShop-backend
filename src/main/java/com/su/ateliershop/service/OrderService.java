package com.su.ateliershop.service;

import com.su.ateliershop.dto.OrderVO;
import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderVO> listOrders(Long userId, Long adminId);
    OrderVO getById(Long id);
    OrderVO createOrder(Long userId, Map<String, Object> body);
    OrderVO payOrder(Long id);
    OrderVO cancelOrder(Long id);
    OrderVO shipOrder(Long id);
    OrderVO completeOrder(Long id);
    OrderVO updateStatus(Long id, Integer status);
}
