package com.su.ateliershop.controller;

import com.su.ateliershop.common.AuthContext;
import com.su.ateliershop.entity.Admin;
import com.su.ateliershop.entity.User;
import com.su.ateliershop.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthContext authContext;

    public AuthController(AuthService authService, AuthContext authContext) {
        this.authService = authService;
        this.authContext = authContext;
    }

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> body) {
        return authService.loginUser(body.get("username"), body.get("password"));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(body));
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return authService.getUserById(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return authService.updateUser(id, body);
    }

    @PostMapping("/admin/login")
    public Admin adminLogin(@RequestBody Map<String, String> body) {
        return authService.loginAdmin(body.get("username"), body.get("password"));
    }

    @GetMapping("/admins")
    public List<Admin> listAdmins(HttpServletRequest request) {
        authContext.requireAdminRole(request);
        return authService.listAdmins();
    }
}
