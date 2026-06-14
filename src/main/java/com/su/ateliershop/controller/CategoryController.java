package com.su.ateliershop.controller;

import com.su.ateliershop.common.AuthContext;
import com.su.ateliershop.entity.Category;
import com.su.ateliershop.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final AuthContext authContext;

    public CategoryController(CategoryService categoryService, AuthContext authContext) {
        this.categoryService = categoryService;
        this.authContext = authContext;
    }

    @GetMapping
    public List<Category> list() {
        return categoryService.listAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Map<String, String> body, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(body));
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Map<String, String> body, HttpServletRequest request) {
        authContext.requireAdmin(request);
        return categoryService.update(id, body);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id, HttpServletRequest request) {
        authContext.requireAdmin(request);
        categoryService.delete(id);
        return Map.of("message", "已删除");
    }
}
