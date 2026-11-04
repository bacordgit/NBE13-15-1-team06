package com.kdt.nbe13151team06.orderItems;


import com.kdt.nbe13151team06.menus.Menus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {
boolean existsByMenu(Menus menu);
}
