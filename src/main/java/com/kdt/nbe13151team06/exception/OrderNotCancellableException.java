package com.kdt.nbe13151team06.exception;

public class OrderNotCancellableException extends RuntimeException {
    public OrderNotCancellableException(String message) {
        super(message);
    }
}
