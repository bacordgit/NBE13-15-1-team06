package com.kdt.nbe13151team06.error;

public class ORDER_NOT_FOUND extends RuntimeException {
    public ORDER_NOT_FOUND(String message) {
        super("주문을 찾을 수 없습니다.");
    }
}
