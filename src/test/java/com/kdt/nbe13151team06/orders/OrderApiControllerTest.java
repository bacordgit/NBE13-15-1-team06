package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.error.ADDRESS_MISMATCH;
import com.kdt.nbe13151team06.error.exceptionhandler.ApiExceptionAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
@Import(ApiExceptionAdvice.class)
public class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("A11 - 이메일 형식이 틀린 주문 접수는 400 VALIDATION_ERROR이고 fieldErrors에 email이 담긴다")
    void createOrder_invalidEmail_400() throws Exception {
        String body = """
                {
                  "email": "not-an-email",
                  "address": "서울시 실습로 10 101호",
                  "postcode": "01234",
                  "items": [ { "menuId": 1, "quantity": 1 } ]
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("입력값을 확인해 주세요."))
                .andExpect(jsonPath("$.fieldErrors.email").exists());

        verifyNoInteractions(orderService);
    }

    @Test
    @DisplayName("A11 - 같은 이메일·배송일에 다른 배송지로 접수하면 409 ADDRESS_MISMATCH이고 fieldErrors는 비어 있다")
    void createOrder_addressMismatch_409() throws Exception {
        given(orderService.create(any(OrderCreateRequest.class))).willThrow(new ADDRESS_MISMATCH());

        String body = """
                {
                  "email": "buyer@example.com",
                  "address": "부산시 다른길 99",
                  "postcode": "01234",
                  "items": [ { "menuId": 1, "quantity": 1 } ]
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ADDRESS_MISMATCH"))
                .andExpect(jsonPath("$.message").value("같은 이메일과 배송일의 기존 주문과 배송지가 다릅니다."))
                .andExpect(jsonPath("$.fieldErrors").isEmpty());
    }
}
