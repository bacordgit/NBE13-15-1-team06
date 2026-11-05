package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.NotNull;
//menu의 availabilty 변수
public record MenuavailableRequest(
        @NotNull Boolean available
) {
}
