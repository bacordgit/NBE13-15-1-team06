package com.kdt.nbe13151team06.error;

public class CATEGORY_NOT_FOUND extends RuntimeException {
    public CATEGORY_NOT_FOUND() {
        super("카테고리를 찾을 수 없습니다.");
    }
}
