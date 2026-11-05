package com.kdt.nbe13151team06.statistics.controller;

import com.kdt.nbe13151team06.statistics.dto.DeliveryBatchResponse;
import com.kdt.nbe13151team06.statistics.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DeliveryBatchController {

    private final StatisticsService statisticsService;

    public DeliveryBatchController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // A14 - 배송 묶음
    @GetMapping("/api/v1/delivery-batches")
    public List<DeliveryBatchResponse> getDeliveryBatches(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shippingDate) {
        return statisticsService.getDeliveryBatches(shippingDate);
    }
}