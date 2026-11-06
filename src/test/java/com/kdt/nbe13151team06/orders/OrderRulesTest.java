package com.kdt.nbe13151team06.orders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 배송일 계산과 취소 마감(서울 시각 14:00:00) 규칙을 시계 없이 값만 넣어 확인한다.
// OrderRules가 같은 패키지의 클래스라서 같은 패키지에서 바로 호출한다.
class OrderRulesTest {

    @Test
    @DisplayName("13:59:59에 접수하면 당일이 배송 대상일이다")
    void shippingDateBeforeCutoffIsSameDay() {
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 13, 59, 59);

        assertEquals(LocalDate.of(2026, 11, 3), OrderRules.shippingDateFor(now));
    }

    @Test
    @DisplayName("14:00:00부터 접수하면 다음 날이 배송 대상일이다")
    void shippingDateAtCutoffIsNextDay() {
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 14, 0, 0);

        assertEquals(LocalDate.of(2026, 11, 4), OrderRules.shippingDateFor(now));
    }

    @Test
    @DisplayName("14:00:01에 접수해도 다음 날이 배송 대상일이다")
    void shippingDateJustAfterCutoffIsNextDay() {
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 14, 0, 1);

        assertEquals(LocalDate.of(2026, 11, 4), OrderRules.shippingDateFor(now));
    }

    @Test
    @DisplayName("연말 12/31 14:00에 접수하면 다음 해 1/1이 배송 대상일이다")
    void shippingDateAtYearEnd() {
        LocalDateTime now = LocalDateTime.of(2026, 12, 31, 14, 0, 0);

        assertEquals(LocalDate.of(2027, 1, 1), OrderRules.shippingDateFor(now));
    }

    @Test
    @DisplayName("배송일 13:59:59까지는 취소할 수 있다")
    void cancelBeforeDeadline() {
        LocalDate shippingDate = LocalDate.of(2026, 11, 3);
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 13, 59, 59);

        assertTrue(OrderRules.beforeCancelDeadline(now, shippingDate));
    }

    @Test
    @DisplayName("배송일 14:00:00부터는 취소할 수 없다")
    void cancelAtDeadline() {
        LocalDate shippingDate = LocalDate.of(2026, 11, 3);
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 14, 0, 0);

        assertFalse(OrderRules.beforeCancelDeadline(now, shippingDate));
    }

    @Test
    @DisplayName("배송일 전날에는 언제든 취소할 수 있다")
    void cancelDayBeforeShipping() {
        LocalDate shippingDate = LocalDate.of(2026, 11, 4);
        LocalDateTime now = LocalDateTime.of(2026, 11, 3, 15, 0, 0);

        assertTrue(OrderRules.beforeCancelDeadline(now, shippingDate));
    }
}
