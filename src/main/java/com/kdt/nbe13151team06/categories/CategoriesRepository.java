package com.kdt.nbe13151team06.categories;


import com.kdt.nbe13151team06.categories.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    // 전체 조회후 Id를 기준으로 오름차순 정렬
    List<Categories> findAllByOrderByIdAsc();

}
