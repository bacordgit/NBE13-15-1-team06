package com.kdt.nbe13151team06.error;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("카테고리를 찾을 수 없습니다.");
    }
}
