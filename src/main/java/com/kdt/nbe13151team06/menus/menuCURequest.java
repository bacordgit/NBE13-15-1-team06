package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.*;

public record menuCURequest(
        @NotNull Long categoryId,
        @NotBlank @NotNull String name,
        @NotNull @Max(1000000) @Min(1) int price,
        @NotNull @Size(min=0, max=300) String description

) {
    public menuCURequest{
        name=name.strip();
        description=description.strip();
    }
}
