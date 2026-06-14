package com.su.ateliershop.service.impl;

import com.su.ateliershop.common.BusinessException;
import com.su.ateliershop.entity.Category;
import com.su.ateliershop.mapper.CategoryMapper;
import com.su.ateliershop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> listAll() {
        return categoryMapper.findAll();
    }

    @Override
    public Category getById(Long id) {
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new BusinessException("分类不存在", 404);
        }
        return category;
    }

    @Override
    public Category create(Map<String, String> body) {
        Category category = new Category();
        category.setName(body.get("name"));
        category.setIcon(body.get("icon") != null ? body.get("icon") : "📁");
        category.setDescription(body.get("description") != null ? body.get("description") : "");
        categoryMapper.insert(category);
        return categoryMapper.findById(category.getId());
    }

    @Override
    public Category update(Long id, Map<String, String> body) {
        Category existing = categoryMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("分类不存在", 404);
        }
        if (body.containsKey("name")) existing.setName(body.get("name"));
        if (body.containsKey("icon")) existing.setIcon(body.get("icon"));
        if (body.containsKey("description")) existing.setDescription(body.get("description"));
        categoryMapper.update(existing);
        return categoryMapper.findById(id);
    }

    @Override
    public void delete(Long id) {
        if (categoryMapper.findById(id) == null) {
            throw new BusinessException("分类不存在", 404);
        }
        categoryMapper.deleteById(id);
    }
}
