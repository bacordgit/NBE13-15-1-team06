package com.kdt.nbe13151team06.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Long> {
    List<Order> findByEmailOrderByCreatedAtDescIdDesc(String email);

    @Query("""
            select count(o) > 0 from Order o
            where o.email = :email
              and o.shippingDate = :shippingDate
              and o.status = :status
              and (o.address != :address or o.postcode != :postcode)
            """)
    boolean existsOtherAddress(@Param("email") String email,
                                  @Param("shippingDate") LocalDate shippingDate,
                                  @Param("status") Order.OrderStatus status,
                                  @Param("address") String address,
                                  @Param("postcode") String postcode);

    List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            LocalDateTime start, LocalDateTime end);

    List<Order> findByShippingDateAndStatusOrderByEmailAsc(
            LocalDate shippingDate, OrderStatus status);

}