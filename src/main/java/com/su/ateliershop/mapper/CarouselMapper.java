package com.su.ateliershop.mapper;

import com.su.ateliershop.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CarouselMapper {
    List<Carousel> findAll();
}
