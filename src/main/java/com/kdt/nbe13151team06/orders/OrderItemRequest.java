package com.kdt.nbe13151team06.orders;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull Long menuId,
        @NotNull @Min(1) @Max(20) Integer quantity) {
}