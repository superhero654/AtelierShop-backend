package com.su.ateliershop.service;

import com.su.ateliershop.entity.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> listAll();
    Category getById(Long id);
    Category create(Map<String, String> body);
    Category update(Long id, Map<String, String> body);
    void delete(Long id);
}
