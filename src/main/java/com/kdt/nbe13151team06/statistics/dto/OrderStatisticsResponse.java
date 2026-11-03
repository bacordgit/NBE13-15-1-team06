package com.kdt.nbe13151team06.statistics.dto;
import java.time.LocalDate;

public class OrderStatisticsResponse {

    private LocalDate date;
    private int orderCount;
    private int placedOrderCount;
    private int cancelledOrderCount;
    private int totalQuantity;
    private long totalAmount;

    public OrderStatisticsResponse(LocalDate date, int orderCount, int placedOrderCount, int cancelledOrderCount, int totalQuantity, long totalAmount) {

        this.date = date;
        this.orderCount = orderCount;
        this.placedOrderCount = placedOrderCount;
        this.cancelledOrderCount = cancelledOrderCount;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public int getPlacedOrderCount() {
        return placedOrderCount;
    }

    public int getCancelledOrderCount() {
        return cancelledOrderCount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getTotalAmount() {
        return totalAmount;
    }
}
