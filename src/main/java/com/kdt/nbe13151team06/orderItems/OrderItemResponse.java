package com.kdt.nbe13151team06.orderItems;

// 변환은 OrderService.toItemResponse
public record OrderItemResponse(
        Long menuId,
        String menuName,
        int unitPrice,
        int quantity,
        int lineAmount) {
}