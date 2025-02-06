package com.digi.ecommerce.digi_shop.infra.mapper;

import com.digi.ecommerce.digi_shop.api.dto.response.CartDTO;
import com.digi.ecommerce.digi_shop.api.dto.response.CartItemDTO;
import com.digi.ecommerce.digi_shop.repository.entity.Cart;
import com.digi.ecommerce.digi_shop.repository.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", expression = "java(mapCartItems(cart.getCartItems()))")
    CartDTO toCartDTO(Cart cart);

    default List<CartItemDTO> mapCartItems(List<CartItem> cartItems) {
        return cartItems.stream().map(item -> new CartItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice()
        )).collect(Collectors.toList());
    }
}