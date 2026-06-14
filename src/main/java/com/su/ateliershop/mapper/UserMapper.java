package com.su.ateliershop.mapper;

import com.su.ateliershop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    int insert(User user);
    int update(User user);
}
