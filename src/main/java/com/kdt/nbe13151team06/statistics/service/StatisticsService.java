package com.kdt.nbe13151team06.statistics.service;

import com.kdt.nbe13151team06.orders.Order;
import com.kdt.nbe13151team06.orders.OrderRepository;
import com.kdt.nbe13151team06.statistics.dto.CustomerStatisticsResponse;
import com.kdt.nbe13151team06.statistics.dto.OrderStatisticsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final OrderRepository orderRepository;

    public StatisticsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private LocalDateTime startOf(LocalDate date) {
        return date.atStartOfDay();
    }

    private LocalDateTime endOf(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    public OrderStatisticsResponse getOrderStatistics(LocalDate date) {

        List<Order> orders = orderRepository
                .findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startOf(date), endOf(date));

        int placedOrderCount = 0;
        int cancelledOrderCount = 0;
        long totalAmount = 0;

        for (Order order : orders) {
            if (order.getStatus() == Order.OrderStatus.PLACED) {
                placedOrderCount++;
                totalAmount += order.getTotalAmount();
            } else {
                cancelledOrderCount++;
            }
        }

        int totalQuantity = 0;

        return new OrderStatisticsResponse(date, orders.size(),
                placedOrderCount, cancelledOrderCount, totalQuantity, totalAmount);
    }

    public List<CustomerStatisticsResponse> getCustomerStatistics(LocalDate date) {

        List<Order> orders = orderRepository
                .findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startOf(date), endOf(date));

        Map<String, List<Order>> ordersByEmail = new LinkedHashMap<>();

        for (Order order : orders) {
            ordersByEmail.computeIfAbsent(order.getEmail(), k -> new ArrayList<>()).add(order);
        }

        List<CustomerStatisticsResponse> result = new ArrayList<>();

        for (Map.Entry<String, List<Order>> entry : ordersByEmail.entrySet()) {
            String email = entry.getKey();
            List<Order> customerOrders = entry.getValue();

            int placedOrderCount = 0;
            int cancelledOrderCount = 0;
            long totalAmount = 0;

            for (Order order : customerOrders) {
                if (order.getStatus() == order.getStatus().PLACED) {
                    placedOrderCount++;
                    totalAmount += order.getTotalAmount();
                } else {
                    cancelledOrderCount++;
                }
            }
            result.add(new CustomerStatisticsResponse(date, email, customerOrders.size(),
                    placedOrderCount, cancelledOrderCount, 0, totalAmount));
        }
        return result;
    }






}
