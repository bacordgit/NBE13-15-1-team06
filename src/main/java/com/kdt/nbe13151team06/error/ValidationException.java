package com.kdt.nbe13151team06.error;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("입력값을 확인해 주세요.");
    }

}

