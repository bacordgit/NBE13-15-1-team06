package com.kdt.nbe13151team06.menus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface menuRepository extends JpaRepository<menus,Long> {
List<menus> findAllByOrderByIdAsc();
}
