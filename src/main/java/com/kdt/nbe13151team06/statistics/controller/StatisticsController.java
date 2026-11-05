package com.kdt.nbe13151team06.statistics.controller;

import com.kdt.nbe13151team06.statistics.dto.CustomerStatisticsResponse;
import com.kdt.nbe13151team06.statistics.dto.MenuStatisticsResponse;
import com.kdt.nbe13151team06.statistics.dto.OrderStatisticsResponse;
import com.kdt.nbe13151team06.statistics.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // A17 - 접수일별 주문 통계
    @GetMapping("/orders")
    public OrderStatisticsResponse getOrderStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return statisticsService.getOrderStatistics(date);
    }

    // A18 - 접수일별 메뉴 통계
    @GetMapping("/menus")
    public List<MenuStatisticsResponse> getMenuStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return statisticsService.getMenuStatistics(date);
    }

    // A24 - 접수일별 고객 통계
    @GetMapping("/customers")
    public List<CustomerStatisticsResponse> getCustomerStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return statisticsService.getCustomerStatistics(date);
    }
}