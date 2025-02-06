package com.digi.ecommerce.digi_shop.service;

import com.digi.ecommerce.digi_shop.api.dto.response.OrderDTO;
import com.digi.ecommerce.digi_shop.infra.exception.EmptyCartException;
import com.digi.ecommerce.digi_shop.infra.exception.EntityNotFoundException;
import com.digi.ecommerce.digi_shop.infra.mapper.OrderMapper;
import com.digi.ecommerce.digi_shop.repository.entity.Cart;
import com.digi.ecommerce.digi_shop.repository.entity.Order;
import com.digi.ecommerce.digi_shop.repository.entity.OrderItem;
import com.digi.ecommerce.digi_shop.repository.entity.User;
import com.digi.ecommerce.digi_shop.repository.repos.cart.CartRepository;
import com.digi.ecommerce.digi_shop.repository.repos.order.OrderRepository;
import com.digi.ecommerce.digi_shop.repository.repos.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, User.class));

        // Retrieve the user's cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("No cart found, Start a new cart and add items"));

        if(cart.getCartItems().isEmpty()){
            throw new EmptyCartException("Cannot place an order with an empty cart.");
        }
        // Create an order from the cart
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderItems(cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList()));
        order.setTotalAmount(cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setStatus("PENDING");
        order.setCreatedAt(Instant.now());

        // Save
        orderRepository.save(order);

        // Clear user's cart
        cart.getCartItems().forEach(cartItem -> cartItem.setCart(null));  // Explicitly dissociate to trigger orphan removal
        cart.getCartItems().clear();  // Clear the cartItems list
        cartRepository.save(cart);

        // Return the order as DTO
        return OrderMapper.INSTANCE.orderToOrderDTO(order);
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return OrderMapper.INSTANCE.orderToOrderDTO(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user with id " + userId);
        }
        return OrderMapper.INSTANCE.ordersToOrderDTOs(orders);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));

        order.setStatus(newStatus);
        orderRepository.save(order);

        return  OrderMapper.INSTANCE.orderToOrderDTO(order);
    }
}
