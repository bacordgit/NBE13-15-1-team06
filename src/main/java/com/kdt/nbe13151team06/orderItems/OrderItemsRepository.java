package com.kdt.nbe13151team06.orderItems;


import com.kdt.nbe13151team06.menus.Menus;
import com.kdt.nbe13151team06.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {
    boolean existsByMenu(Menus menu);
    List<OrderItems> findByOrderIdIn(List<Order> orders);
}
