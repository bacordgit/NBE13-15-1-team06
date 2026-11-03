package com.kdt.nbe13151team06.orders;

import java.util.List;

public record OrderCreateRequest(
        String email,
        String address,
        String postcode,
        List<OrderItemRequest> items) {}