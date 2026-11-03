package com.kdt.nbe13151team06.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriesCreateRequest(@NotBlank @Size(max = 20) String name) {
}
