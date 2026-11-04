package com.kdt.nbe13151team06.menus;

import jakarta.validation.constraints.NotNull;

public record menuavailableRequest(
        @NotNull boolean availability
) {
}
