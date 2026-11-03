package com.kdt.nbe13151team06.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Long> {

    List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            LocalDateTime start, LocalDateTime end);

    List<Order> findByShippingDateAndStatusOrderByEmailAsc(
            LocalDate shippingDate, OrderStatus status);

}