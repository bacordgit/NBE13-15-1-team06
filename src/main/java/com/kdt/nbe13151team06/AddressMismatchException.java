package com.kdt.nbe13151team06;

public class AddressMismatchException extends RuntimeException {
    public AddressMismatchException() {
        super("기존 주문과 배송지가 다릅니다.");
    }
}