package com.kdt.nbe13151team06.orderItems;

import com.kdt.nbe13151team06.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<orderItems, Long> {


    List<orderItems> findByOrderIdIn(List<Order> orders);
}