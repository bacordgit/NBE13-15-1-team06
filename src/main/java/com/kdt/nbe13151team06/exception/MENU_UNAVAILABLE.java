package com.kdt.nbe13151team06.exception;

public class MENU_UNAVAILABLE extends RuntimeException {
    public MENU_UNAVAILABLE(String message) {
        super("판매 중지된 메뉴가 포함되어 있습니다.");
    }
}
