package com.kdt.nbe13151team06.statistics.service;

import com.kdt.nbe13151team06.statistics.dto.OrderStatisticsResponse;
import com.kdt.nbe13151team06.orders.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatisticsService {
    private final OrderRepository orderRepository;

    public StatisticsService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public OrderStatisticsResponse getOrderStatistics(LocalDate date){
        L
    }
}
