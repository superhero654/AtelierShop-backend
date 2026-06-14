package com.su.ateliershop.mapper;

import com.su.ateliershop.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdminMapper {
    Admin findById(Long id);
    Admin findByUsername(String username);
    List<Admin> findAll();
}
