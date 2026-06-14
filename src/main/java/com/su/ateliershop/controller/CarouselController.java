package com.su.ateliershop.controller;

import com.su.ateliershop.entity.Carousel;
import com.su.ateliershop.service.CarouselService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @GetMapping
    public List<Carousel> list() {
        return carouselService.listAll();
    }
}
