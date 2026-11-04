package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.orderItems.OrderItemResponse;
import java.util.List;


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