package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.response.OrderDTO;
import com.digi.ecommerce.digi_shop.api.dto.response.OrderItemDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Order;
import com.digi.ecommerce.digi_shop.repository.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
}