package com.kdt.nbe13151team06.error;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException() {
        super("메뉴를 찾을 수 없습니다.");
    }
}
