package com.kdt.nbe13151team06;

import com.kdt.nbe13151team06.statistics.controller.DeliveryBatchController;
import com.kdt.nbe13151team06.statistics.dto.DeliveryBatchResponse;
import com.kdt.nbe13151team06.statistics.dto.DeliveryItemResponse;
import com.kdt.nbe13151team06.statistics.service.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryBatchController.class)
class DeliveryBatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatisticsService statisticsService;

    @Test
    @DisplayName("A14 배송 묶음은 배송일을 받으면 200과 함께 고객별 주소, 주문번호, 합산된 상품 목록을 반환한다")
    void 배송묶음_정상조회() throws Exception {
        LocalDate shippingDate = LocalDate.of(2026, 11, 5);

        DeliveryItemResponse item =
                new DeliveryItemResponse(1L, "Columbia Nariñó", 5000, 5, 25000L);

        DeliveryBatchResponse batch = new DeliveryBatchResponse(
                shippingDate, "a@x.com", "서울시 강남구", "06000",
                List.of(10L, 12L), 2, 5, 25000L, List.of(item));

        given(statisticsService.getDeliveryBatches(shippingDate)).willReturn(List.of(batch));

        mockMvc.perform(get("/api/v1/delivery-batches").param("shippingDate", "2026-11-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("a@x.com"))
                .andExpect(jsonPath("$[0].address").value("서울시 강남구"))
                .andExpect(jsonPath("$[0].postcode").value("06000"))
                .andExpect(jsonPath("$[0].orderIds[0]").value(10))
                .andExpect(jsonPath("$[0].orderIds[1]").value(12))
                .andExpect(jsonPath("$[0].orderCount").value(2))
                .andExpect(jsonPath("$[0].totalQuantity").value(5))
                .andExpect(jsonPath("$[0].totalAmount").value(25000))
                .andExpect(jsonPath("$[0].items[0].menuName").value("Columbia Nariñó"))
                .andExpect(jsonPath("$[0].items[0].quantity").value(5));
    }
}