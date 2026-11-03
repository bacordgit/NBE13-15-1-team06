package com.kdt.nbe13151team06;

public class ADDRESS_MISMATCH extends RuntimeException {
    public ADDRESS_MISMATCH() {
        super("기존 주문과 배송지가 다릅니다.");
    }
}