package com.su.ateliershop.mapper;

import com.su.ateliershop.entity.ShopOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ShopOrderMapper {
    List<ShopOrder> findAll();
    List<ShopOrder> findByUserId(Long userId);
    ShopOrder findById(Long id);
    int insert(ShopOrder order);
    int update(ShopOrder order);
    int countByDatePrefix(@Param("prefix") String prefix);
}
