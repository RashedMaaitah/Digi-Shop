package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Long userId);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getOrdersByUserId(Long userId);

    OrderDTO updateOrderStatus(Long orderId, String newStatus);
}
