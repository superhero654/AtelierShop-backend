package com.su.ateliershop.service.impl;

import com.su.ateliershop.entity.Carousel;
import com.su.ateliershop.mapper.CarouselMapper;
import com.su.ateliershop.service.CarouselService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    private final CarouselMapper carouselMapper;

    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }

    @Override
    public List<Carousel> listAll() {
        return carouselMapper.findAll();
    }
}
