package com.su.ateliershop.service.impl;

import com.su.ateliershop.common.BusinessException;
import com.su.ateliershop.entity.Admin;
import com.su.ateliershop.entity.User;
import com.su.ateliershop.mapper.AdminMapper;
import com.su.ateliershop.mapper.UserMapper;
import com.su.ateliershop.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserMapper userMapper, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loginUser(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new BusinessException("请输入用户名和密码");
        }
        User user = userMapper.findByUsername(username);
        if (user == null) {
            user = userMapper.findByEmail(username);
        }
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误", 401);
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public User registerUser(Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        String nickname = body.get("nickname");

        List<String> errors = new ArrayList<>();
        if (username == null || username.isBlank()) errors.add("用户名不能为空");
        if (email == null || email.isBlank()) errors.add("邮箱不能为空");
        if (password == null || password.length() < 6) errors.add("密码至少 6 位");
        if (!errors.isEmpty()) {
            throw new BusinessException(String.join("；", errors));
        }

        if (userMapper.findByUsername(username) != null || userMapper.findByEmail(email) != null) {
            throw new BusinessException("用户名或邮箱已被注册", 409);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname != null && !nickname.isBlank() ? nickname : username);
        user.setPhone("");
        user.setAddress("");
        userMapper.insert(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在", 404);
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public User updateUser(Long id, Map<String, String> body) {
        User existing = userMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("用户不存在", 404);
        }
        if (body.containsKey("nickname")) existing.setNickname(body.get("nickname"));
        if (body.containsKey("phone")) existing.setPhone(body.get("phone"));
        if (body.containsKey("address")) existing.setAddress(body.get("address"));
        if (body.containsKey("email")) existing.setEmail(body.get("email"));
        userMapper.update(existing);
        existing.setPassword(null);
        return existing;
    }

    @Override
    public Admin loginAdmin(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new BusinessException("请输入管理员账号和密码");
        }
        Admin admin = adminMapper.findByUsername(username);
        if (admin == null || !passwordEncoder.matches(password, admin.getPassword())) {
            throw new BusinessException("管理员账号或密码错误", 401);
        }
        admin.setPassword(null);
        return admin;
    }

    @Override
    public List<Admin> listAdmins() {
        List<Admin> list = adminMapper.findAll();
        list.forEach(a -> a.setPassword(null));
        return list;
    }
}
