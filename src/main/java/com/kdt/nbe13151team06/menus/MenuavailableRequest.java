package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.NotNull;

public record MenuavailableRequest(
        @NotNull Boolean available
) {
}
