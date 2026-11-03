package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.orderItems.OrderItemResponse;
import java.util.List;

// 필드만 가진 record (변환은 OrderService.toResponse)
public record OrderResponse(
        Long id,
        String email,
        String address,
        String postcode,
        String createdAt,
        String shippingDate,
        long totalAmount,
        List<OrderItemResponse> items,
        String status,
        String cancelledAt,
        String memo) {
}