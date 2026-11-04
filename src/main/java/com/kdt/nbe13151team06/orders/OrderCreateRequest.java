package com.kdt.nbe13151team06.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderCreateRequest(
        @NotNull
        @Size(min = 1, max = 100)
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+$")
        String email,
        @NotBlank @Size(max = 200) String address,
        @NotNull @Pattern(regexp = "^[0-9]{5}$") String postcode,
        @NotEmpty @Size(max = 10) @Valid List<OrderItemRequest> items) {
}