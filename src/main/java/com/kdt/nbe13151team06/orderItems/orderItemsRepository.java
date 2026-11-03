package com.kdt.nbe13151team06.orderItems;


import com.kdt.nbe13151team06.menus.menus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderItemsRepository extends JpaRepository<orderItems,Long> {
boolean findByMenu(menus menu);
}
