package com.kdt.nbe13151team06.error.exceptionhandler;

import java.util.Map;

public record ApiError(String code, String message, Map<String, String> fieldErrors) {

    public ApiError(String code, String message) {
        this(code, message, Map.of());
    }
}