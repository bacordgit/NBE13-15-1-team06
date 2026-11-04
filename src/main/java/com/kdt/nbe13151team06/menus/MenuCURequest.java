package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.*;

public record MenuCURequest(
        @NotNull Long categoryId,
        @NotBlank @NotNull String name,
        @NotNull @Max(1000000) @Min(1) int price,
        @NotNull @Size(max=300) String description

) {
    public MenuCURequest{
        name = (name == null) ? null : name.strip();
        description = (description == null) ? null : description.strip();
    }
}
