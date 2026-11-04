package com.kdt.nbe13151team06.categories.dto;

import com.kdt.nbe13151team06.categories.domain.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriesResponse(Long id, String name) {

    public static CategoriesResponse from(Categories categories){
        return new CategoriesResponse(categories.getId(), categories.getName());
    }

}
