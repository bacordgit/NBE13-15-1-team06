package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.*;
//menu create,update에 쓰이는 dto
public record MenuCURequest(
        @NotNull Long categoryId,
        @NotBlank @NotNull String name,
        @NotNull @Max(1000000) @Min(1) int price,
        @NotNull @Size(max=300) String description

) {
    public MenuCURequest{
        //앞 뒤의 공백 제거
        name = (name == null) ? null : name.strip();
        description = (description == null) ? null : description.strip();
    }
}
