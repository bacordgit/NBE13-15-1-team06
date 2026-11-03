package com.kdt.nbe13151team06.orders;

// A12 목록용 (변환은 OrderService.toListResponse)
public record OrderListResponse(
        Long id,
        String createdAt,
        String shippingDate,
        long totalAmount,
        String status) {
}