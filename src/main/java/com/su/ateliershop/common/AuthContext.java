package com.su.ateliershop.common;

import com.su.ateliershop.entity.Admin;
import com.su.ateliershop.mapper.AdminMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    private final AdminMapper adminMapper;

    public AuthContext(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public Long getUserId(HttpServletRequest request) {
        String header = request.getHeader("X-User-Id");
        if (header == null || header.isBlank()) return null;
        try {
            return Long.parseLong(header.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getAdminId(HttpServletRequest request) {
        String header = request.getHeader("X-Admin-Id");
        if (header == null || header.isBlank()) return null;
        try {
            return Long.parseLong(header.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void requireUser(HttpServletRequest request) {
        if (getUserId(request) == null) {
            throw new BusinessException("请先登录", 401);
        }
    }

    public void requireAdmin(HttpServletRequest request) {
        if (getAdminId(request) == null) {
            throw new BusinessException("请先登录管理员账号", 401);
        }
    }

    public Admin requireAdminRole(HttpServletRequest request) {
        Long adminId = getAdminId(request);
        if (adminId == null) {
            throw new BusinessException("请先登录管理员账号", 401);
        }
        Admin admin = adminMapper.findById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在", 401);
        }
        if (!"admin".equals(admin.getRole())) {
            throw new BusinessException("权限不足", 403);
        }
        return admin;
    }
}
