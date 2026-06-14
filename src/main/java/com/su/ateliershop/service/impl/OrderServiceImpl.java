package com.su.ateliershop.service.impl;

import com.su.ateliershop.common.BusinessException;
import com.su.ateliershop.dto.OrderVO;
import com.su.ateliershop.entity.OrderItem;
import com.su.ateliershop.entity.ShopOrder;
import com.su.ateliershop.mapper.OrderItemMapper;
import com.su.ateliershop.mapper.ShopOrderMapper;
import com.su.ateliershop.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final ShopOrderMapper shopOrderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderServiceImpl(ShopOrderMapper shopOrderMapper, OrderItemMapper orderItemMapper) {
        this.shopOrderMapper = shopOrderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public List<OrderVO> listOrders(Long userId, Long adminId) {
        List<ShopOrder> orders;
        if (adminId != null) {
            orders = shopOrderMapper.findAll();
        } else if (userId != null) {
            orders = shopOrderMapper.findByUserId(userId);
        } else {
            orders = new ArrayList<>();
        }
        return orders.stream().map(this::toVO).toList();
    }

    @Override
    public OrderVO getById(Long id) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) {
            throw new BusinessException("订单不存在", 404);
        }
        return toVO(order);
    }

    @Override
    @Transactional
    public OrderVO createOrder(Long userId, Map<String, Object> body) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
        String address = (String) body.get("address");
        String receiver = (String) body.get("receiver");
        String phone = (String) body.get("phone");
        Object totalPriceObj = body.get("totalPrice");

        List<String> errors = new ArrayList<>();
        if (items == null || items.isEmpty()) errors.add("订单商品不能为空");
        if (address == null || address.isBlank()) errors.add("收货地址不能为空");
        if (receiver == null || receiver.isBlank()) errors.add("收货人不能为空");
        if (phone == null || phone.isBlank()) errors.add("手机号不能为空");
        if (!errors.isEmpty()) {
            throw new BusinessException(String.join("；", errors));
        }

        ShopOrder order = new ShopOrder();
        order.setUserId(userId);
        order.setOrderNo(generateOrderNo());
        order.setCreateTime(LocalDateTime.now());
        order.setPayTime(null);
        order.setStatus(0);
        order.setTotalPrice(totalPriceObj instanceof Number ? ((Number) totalPriceObj).doubleValue() : Double.parseDouble(totalPriceObj.toString()));
        order.setAddress(address);
        order.setReceiver(receiver);
        order.setPhone(phone);
        order.setLogisticsCompany(null);
        order.setTrackingNo(null);
        order.setLogisticsStatus(null);
        shopOrderMapper.insert(order);

        for (Map<String, Object> item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setGoodId(((Number) item.get("goodId")).longValue());
            orderItem.setCount(((Number) item.get("count")).intValue());
            orderItem.setPrice(((Number) item.get("price")).doubleValue());
            orderItemMapper.insert(orderItem);
        }

        return toVO(shopOrderMapper.findById(order.getId()));
    }

    @Override
    public OrderVO payOrder(Long id) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) throw new BusinessException("订单不存在", 404);
        if (order.getStatus() != 0) throw new BusinessException("订单状态不允许支付");
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        shopOrderMapper.update(order);
        return toVO(shopOrderMapper.findById(id));
    }

    @Override
    public OrderVO cancelOrder(Long id) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) throw new BusinessException("订单不存在", 404);
        if (order.getStatus() != 0) throw new BusinessException("当前状态不允许取消");
        order.setStatus(4);
        shopOrderMapper.update(order);
        return toVO(shopOrderMapper.findById(id));
    }

    @Override
    public OrderVO shipOrder(Long id) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) throw new BusinessException("订单不存在", 404);
        if (order.getStatus() != 1) throw new BusinessException("订单状态不允许发货");
        order.setStatus(2);
        order.setLogisticsCompany("顺丰速运");
        order.setTrackingNo("SF" + System.currentTimeMillis());
        order.setLogisticsStatus("运输中");
        shopOrderMapper.update(order);
        return toVO(shopOrderMapper.findById(id));
    }

    @Override
    public OrderVO completeOrder(Long id) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) throw new BusinessException("订单不存在", 404);
        if (order.getStatus() != 2) throw new BusinessException("订单状态不允许确认收货");
        order.setStatus(3);
        if (order.getLogisticsStatus() != null) {
            order.setLogisticsStatus("已签收");
        }
        shopOrderMapper.update(order);
        return toVO(shopOrderMapper.findById(id));
    }

    @Override
    public OrderVO updateStatus(Long id, Integer status) {
        ShopOrder order = shopOrderMapper.findById(id);
        if (order == null) throw new BusinessException("订单不存在", 404);
        if (status == null) throw new BusinessException("缺少 status 字段");
        order.setStatus(status);
        shopOrderMapper.update(order);
        return toVO(shopOrderMapper.findById(id));
    }

    private String generateOrderNo() {
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int count = shopOrderMapper.countByDatePrefix(datePrefix);
        return datePrefix + String.format("%04d", count + 1);
    }

    private OrderVO toVO(ShopOrder order) {
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setUserId(order.getUserId());
        vo.setOrderNo(order.getOrderNo());
        vo.setCreateTime(order.getCreateTime());
        vo.setPayTime(order.getPayTime());
        vo.setStatus(order.getStatus());
        vo.setTotalPrice(order.getTotalPrice());
        vo.setAddress(order.getAddress());
        vo.setReceiver(order.getReceiver());
        vo.setPhone(order.getPhone());

        List<OrderVO.OrderItemVO> items = new ArrayList<>();
        for (OrderItem item : orderItemMapper.findByOrderId(order.getId())) {
            OrderVO.OrderItemVO itemVO = new OrderVO.OrderItemVO();
            itemVO.setGoodId(item.getGoodId());
            itemVO.setCount(item.getCount());
            itemVO.setPrice(item.getPrice());
            items.add(itemVO);
        }
        vo.setItems(items);

        if (order.getLogisticsCompany() != null) {
            Map<String, String> logistics = new HashMap<>();
            logistics.put("company", order.getLogisticsCompany());
            logistics.put("trackingNo", order.getTrackingNo());
            logistics.put("status", order.getLogisticsStatus());
            vo.setLogistics(logistics);
        } else {
            vo.setLogistics(null);
        }
        return vo;
    }
}
