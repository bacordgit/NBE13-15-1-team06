package com.kdt.nbe13151team06.orders;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderUpdateRequest(
        @NotNull @Size(max = 200) String memo) {}