package com.kdt.nbe13151team06.error;

public class MENU_NOT_FOUND extends RuntimeException {
    public MENU_NOT_FOUND() {
        super("메뉴를 찾을 수 없습니다.");
    }
}
