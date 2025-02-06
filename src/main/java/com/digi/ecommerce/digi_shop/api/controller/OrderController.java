package com.digi.ecommerce.digi_shop.api.controller;

import com.digi.ecommerce.digi_shop.api.dto.request.OrderStatusUpdateDTO;
import com.digi.ecommerce.digi_shop.api.dto.response.ApiResponse;
import com.digi.ecommerce.digi_shop.api.dto.response.OrderDTO;
import com.digi.ecommerce.digi_shop.infra.security.dto.UserDetailsDTO;
import com.digi.ecommerce.digi_shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.digi.ecommerce.digi_shop.common.PathConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping(ORDER_BASE)
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest httpServletRequest;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> placeOrder(
            @AuthenticationPrincipal UserDetailsDTO userDetails) {
        OrderDTO createdOrder = orderService.placeOrder(userDetails.getId());
        return ResponseEntity
                .status(CREATED)
                .body(
                        ApiResponse.success(
                                List.of(createdOrder),
                                "Order created successfully",
                                httpServletRequest.getRequestURI()
                        ));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(ORDER_ID)
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderDetails(
            @PathVariable
            @Min(value = 1, message = "Order id can't be less than one")
            Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        List.of(orderDTO),
                        "Fetched Order Details Successfully",
                        httpServletRequest.getRequestURI()
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderDetailsForCurrentUser(
            @AuthenticationPrincipal UserDetailsDTO userDetails) {

        List<OrderDTO> orderDTOs = orderService.getOrdersByUserId(userDetails.getId());
        return ResponseEntity.ok(
                ApiResponse.success(
                        orderDTOs,
                        "Fetched Orders for current user successfully",
                        httpServletRequest.getRequestURI()
                ));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(ORDER_USER_ID)
    public ResponseEntity<ApiResponse<OrderDTO>> getAllUserOrders(
            @PathVariable
            @Min(value = 1, message = "user id can't be less than one")
            Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        orders,
                        "Fetched Orders for current user successfully",
                        httpServletRequest.getRequestURI()
                ));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(ORDER_ID_STATUS)
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @PathVariable
            @Min(value = 1, message = "Order id can't be less than one")
            Long orderId,
            @RequestBody @Valid OrderStatusUpdateDTO statusUpdateDTO) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, statusUpdateDTO.status());
        return ResponseEntity.ok(
                ApiResponse.success(
                        List.of(updatedOrder),
                        "Updated order status to" + statusUpdateDTO.status(),
                        httpServletRequest.getRequestURI()
                ));
    }

}
