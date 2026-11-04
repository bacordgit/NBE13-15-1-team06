package com.kdt.nbe13151team06.error;

public class ORDER_NOT_CANCELLABLE extends RuntimeException {
    public ORDER_NOT_CANCELLABLE(String message) {
        super("배송 대상일 14시 전의 접수 상태 주문만 취소할 수 있습니다.");
    }
}
