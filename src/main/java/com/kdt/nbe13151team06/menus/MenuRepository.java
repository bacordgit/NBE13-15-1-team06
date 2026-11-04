package com.kdt.nbe13151team06.menus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menus,Long> {
    List<Menus> findAllByOrderByIdAsc();

    List<Menus> findAllByCategoriesIdOrderByIdAsc(Long id);
}
