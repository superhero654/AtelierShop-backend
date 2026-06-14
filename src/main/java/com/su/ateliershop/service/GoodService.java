package com.su.ateliershop.service;

import com.su.ateliershop.entity.Good;
import java.util.List;
import java.util.Map;

public interface GoodService {
    List<Good> listGoods(String status, Long categoryId, Boolean hot, String keyword);
    Good getById(Long id);
    Good createGood(Map<String, Object> body);
    Good updateGood(Long id, Map<String, Object> body);
    void deleteGood(Long id);
    Good toggleStatus(Long id);
}
