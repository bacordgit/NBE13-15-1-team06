package com.kdt.nbe13151team06.orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

// 주문 규칙
final class OrderRules {

    private OrderRules() {
    }

    static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    //당일 배송 판단
    static LocalDate shippingDateFor(LocalDateTime now) {
        if (now.getHour() < 14) {
            return now.toLocalDate();
        }
        return now.toLocalDate().plusDays(1);
    }
    // 취소가능 판단
    static boolean beforeCancelDeadline(LocalDateTime now, LocalDate shippingDate) {
        return now.isBefore(shippingDate.atTime(14, 0, 0));
    }
}