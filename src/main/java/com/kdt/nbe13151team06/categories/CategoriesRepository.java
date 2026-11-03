package com.kdt.nbe13151team06.categories;


import com.kdt.nbe13151team06.categories.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
