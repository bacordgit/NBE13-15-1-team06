package com.kdt.nbe13151team06.orders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// 2026-11-03T09:12:36+09:00 형식
final class OrderTimeFormat {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    private OrderTimeFormat() {
    }

    static String format(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.of("Asia/Seoul")).format(FORMAT);
    }
}