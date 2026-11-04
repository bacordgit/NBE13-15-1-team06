package com.kdt.nbe13151team06.statistics.dto;

import java.time.LocalDate;
import java.util.List;

public class DeliveryBatchResponse {

    private final LocalDate shippingDate;
    private final String email;
    private final String address;
    private final String postcode;
    private final List<Long> orderIds;
    private final int orderCount;
    private final int totalQuantity;
    private final long totalAmount;
    private final List<DeliveryItemResponse> items;

    public DeliveryBatchResponse(LocalDate shippingDate, String email, String address, String postcode,
                                 List<Long> orderIds, int orderCount, int totalQuantity,
                                 long totalAmount, List<DeliveryItemResponse> items) {
        this.shippingDate = shippingDate;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderIds = orderIds;
        this.orderCount = orderCount;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public List<DeliveryItemResponse> getItems() {
        return items;
    }
}