package com.su.ateliershop.controller;

import com.su.ateliershop.common.AuthContext;
import com.su.ateliershop.dto.OrderVO;
import com.su.ateliershop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final AuthContext authContext;

    public OrderController(OrderService orderService, AuthContext authContext) {
        this.orderService = orderService;
        this.authContext = authContext;
    }

    @GetMapping
    public List<OrderVO> list(HttpServletRequest request) {
        Long userId = authContext.getUserId(request);
        Long adminId = authContext.getAdminId(request);
        return orderService.listOrders(userId, adminId);
    }

    @GetMapping("/{id}")
    public OrderVO getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    public ResponseEntity<OrderVO> create(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        authContext.requireUser(request);
        Long userId = authContext.getUserId(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, body));
    }

    @PatchMapping("/{id}/pay")
    public OrderVO pay(@PathVariable Long id) {
        return orderService.payOrder(id);
    }

    @PatchMapping("/{id}/cancel")
    public OrderVO cancel(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @PatchMapping("/{id}/ship")
    public OrderVO ship(@PathVariable Long id, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return orderService.shipOrder(id);
    }

    @PatchMapping("/{id}/complete")
    public OrderVO complete(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }

    @PatchMapping("/{id}/status")
    public OrderVO updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpServletRequest request) {
        authContext.requireAdmin(request);
        Integer status = body.get("status") instanceof Number ? ((Number) body.get("status")).intValue() : Integer.parseInt(body.get("status").toString());
        return orderService.updateStatus(id, status);
    }
}
