package com.su.ateliershop.service;

import com.su.ateliershop.entity.Admin;
import com.su.ateliershop.entity.User;
import java.util.List;
import java.util.Map;

public interface AuthService {
    User loginUser(String username, String password);
    User registerUser(Map<String, String> body);
    User getUserById(Long id);
    User updateUser(Long id, Map<String, String> body);
    Admin loginAdmin(String username, String password);
    List<Admin> listAdmins();
}
