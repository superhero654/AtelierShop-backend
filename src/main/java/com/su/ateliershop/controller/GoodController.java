package com.su.ateliershop.controller;

import com.su.ateliershop.common.AuthContext;
import com.su.ateliershop.entity.Good;
import com.su.ateliershop.service.GoodService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodController {

    private final GoodService goodService;
    private final AuthContext authContext;

    public GoodController(GoodService goodService, AuthContext authContext) {
        this.goodService = goodService;
        this.authContext = authContext;
    }

    @GetMapping
    public List<Good> list(@RequestParam(required = false) String status,
                           @RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) String hot,
                           @RequestParam(required = false) String keyword) {
        Boolean hotFlag = hot != null && !hot.isBlank() ? true : null;
        return goodService.listGoods(status, categoryId, hotFlag, keyword);
    }

    @GetMapping("/{id}")
    public Good getById(@PathVariable Long id) {
        return goodService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Good> create(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(goodService.createGood(body));
    }

    @PutMapping("/{id}")
    public Good update(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return goodService.updateGood(id, body);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id, HttpServletRequest request) {
        authContext.requireAdmin(request);
        goodService.deleteGood(id);
        return Map.of("message", "已删除");
    }

    @PatchMapping("/{id}/toggle-status")
    public Good toggleStatus(@PathVariable Long id, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return goodService.toggleStatus(id);
    }
}
