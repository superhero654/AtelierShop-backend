package com.su.ateliershop.service.impl;

import com.su.ateliershop.common.BusinessException;
import com.su.ateliershop.entity.Good;
import com.su.ateliershop.mapper.GoodMapper;
import com.su.ateliershop.service.GoodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {

    private final GoodMapper goodMapper;

    public GoodServiceImpl(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
    }

    @Override
    public List<Good> listGoods(String status, Long categoryId, Boolean hot, String keyword) {
        return goodMapper.findAll(status, categoryId, hot, keyword);
    }

    @Override
    public Good getById(Long id) {
        Good good = goodMapper.findById(id);
        if (good == null) {
            throw new BusinessException("商品不存在", 404);
        }
        return good;
    }

    @Override
    public Good createGood(Map<String, Object> body) {
        String name = (String) body.get("name");
        Object priceObj = body.get("price");
        Object categoryIdObj = body.get("categoryId");
        String img = (String) body.get("img");

        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) errors.add("商品名称不能为空");
        if (priceObj == null) errors.add("价格无效");
        if (categoryIdObj == null) errors.add("分类不能为空");
        if (img == null || img.isBlank()) errors.add("图片地址不能为空");
        if (!errors.isEmpty()) {
            throw new BusinessException(String.join("；", errors));
        }

        Good good = new Good();
        good.setName(name);
        good.setPrice(toDouble(priceObj));
        good.setCategoryId(toLong(categoryIdObj));
        good.setImg(img);
        good.setDescription(body.get("description") != null ? body.get("description").toString() : "");
        good.setStock(body.get("stock") != null ? toInt(body.get("stock")) : 0);
        good.setStatus(body.get("status") != null ? body.get("status").toString() : "on");
        good.setHot(body.get("hot") != null && Boolean.TRUE.equals(body.get("hot")) || "true".equals(String.valueOf(body.get("hot"))));
        goodMapper.insert(good);
        return goodMapper.findById(good.getId());
    }

    @Override
    public Good updateGood(Long id, Map<String, Object> body) {
        Good existing = goodMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("商品不存在", 404);
        }
        if (body.containsKey("name")) existing.setName((String) body.get("name"));
        if (body.containsKey("price")) existing.setPrice(toDouble(body.get("price")));
        if (body.containsKey("categoryId")) existing.setCategoryId(toLong(body.get("categoryId")));
        if (body.containsKey("img")) existing.setImg((String) body.get("img"));
        if (body.containsKey("description")) existing.setDescription(body.get("description").toString());
        if (body.containsKey("stock")) existing.setStock(toInt(body.get("stock")));
        if (body.containsKey("status")) existing.setStatus(body.get("status").toString());
        if (body.containsKey("hot")) {
            Object hot = body.get("hot");
            existing.setHot(Boolean.TRUE.equals(hot) || "true".equals(String.valueOf(hot)));
        }
        goodMapper.update(existing);
        return goodMapper.findById(id);
    }

    @Override
    public void deleteGood(Long id) {
        if (goodMapper.findById(id) == null) {
            throw new BusinessException("商品不存在", 404);
        }
        goodMapper.deleteById(id);
    }

    @Override
    public Good toggleStatus(Long id) {
        Good good = goodMapper.findById(id);
        if (good == null) {
            throw new BusinessException("商品不存在", 404);
        }
        good.setStatus("on".equals(good.getStatus()) ? "off" : "on");
        goodMapper.update(good);
        return goodMapper.findById(id);
    }

    private Double toDouble(Object obj) {
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        return Double.parseDouble(obj.toString());
    }

    private Long toLong(Object obj) {
        if (obj instanceof Number) return ((Number) obj).longValue();
        return Long.parseLong(obj.toString());
    }

    private Integer toInt(Object obj) {
        if (obj instanceof Number) return ((Number) obj).intValue();
        return Integer.parseInt(obj.toString());
    }
}
