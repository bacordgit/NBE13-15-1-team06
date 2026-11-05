package com.kdt.nbe13151team06.menus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menus,Long> {
    List<Menus> findAllByOrderByIdAsc();

    List<Menus> findAllByCategoriesIdOrderByIdAsc(Long id);

    // 카테고리 삭제시 필요. 삭제 전 카테고리 존재 여부 확인
    boolean existsByCategoriesId(Long id);
}
