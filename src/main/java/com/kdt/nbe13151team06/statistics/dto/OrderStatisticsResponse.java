package com.kdt.nbe13151team06.statistics.dto;   // 폴더 위치와 같은 주소

import java.time.LocalDate;

public class OrderStatisticsResponse {

    private LocalDate date;                 // data → date 로 수정됨
    private long orderCount;
    private long placedOrderCount;
    private long cancelledOrderCount;
    private long totalQuantity;
    private long totalAmount;

    public OrderStatisticsResponse(LocalDate date, long orderCount, long placedOrderCount,
                                   long cancelledOrderCount, long totalQuantity, long totalAmount) {
        this.date = date;
        this.orderCount = orderCount;
        this.placedOrderCount = placedOrderCount;
        this.cancelledOrderCount = cancelledOrderCount;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() { return date; }
    public long getOrderCount() { return orderCount; }
    public long getPlacedOrderCount() { return placedOrderCount; }
    public long getCancelledOrderCount() { return cancelledOrderCount; }
    public long getTotalQuantity() { return totalQuantity; }
    public long getTotalAmount() { return totalAmount; }
}