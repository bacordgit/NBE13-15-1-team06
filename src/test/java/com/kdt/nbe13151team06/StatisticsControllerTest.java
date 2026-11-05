package com.kdt.nbe13151team06;

import com.kdt.nbe13151team06.statistics.controller.StatisticsController;
import com.kdt.nbe13151team06.statistics.dto.OrderStatisticsResponse;
import com.kdt.nbe13151team06.statistics.service.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatisticsService statisticsService;

    @Test
    @DisplayName("A17 주문 통계는 날짜를 받으면 200과 함께 주문 수, 취소 수, 수량, 금액 필드를 반환한다")
    void 주문통계_정상조회() throws Exception {
        // given
        LocalDate date = LocalDate.of(2026, 11, 4);
        OrderStatisticsResponse response =
                new OrderStatisticsResponse(date, 3, 2, 1, 6, 30000L);
        given(statisticsService.getOrderStatistics(date)).willReturn(response);

        // when + then
        mockMvc.perform(get("/api/v1/statistics/orders").param("date", "2026-11-04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2026-11-04"))
                .andExpect(jsonPath("$.orderCount").value(3))
                .andExpect(jsonPath("$.placedOrderCount").value(2))
                .andExpect(jsonPath("$.cancelledOrderCount").value(1))
                .andExpect(jsonPath("$.totalQuantity").value(6))
                .andExpect(jsonPath("$.totalAmount").value(30000));
    }
}