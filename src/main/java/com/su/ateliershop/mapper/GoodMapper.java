package com.su.ateliershop.mapper;

import com.su.ateliershop.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GoodMapper {
    List<Good> findAll(@Param("status") String status,
                       @Param("categoryId") Long categoryId,
                       @Param("hot") Boolean hot,
                       @Param("keyword") String keyword);
    Good findById(Long id);
    int insert(Good good);
    int update(Good good);
    int deleteById(Long id);
}
