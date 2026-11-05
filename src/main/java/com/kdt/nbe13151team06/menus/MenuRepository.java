package com.kdt.nbe13151team06.menus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menus,Long> {

    List<Menus> findAllByOrderByIdAsc();//Id 오름차순으로 메뉴 전체 조회

    List<Menus> findAllByCategoriesIdOrderByIdAsc(Long id); // 카탈로그 id로 전체 메뉴 조회

    boolean existsByCategoriesId(Long id);//카탈로그 id로 메뉴가 존재하는지 확인
}
