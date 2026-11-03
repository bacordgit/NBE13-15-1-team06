package com.kdt.nbe13151team06.error;

public class MENU_IN_USE extends RuntimeException {
    public MENU_IN_USE() {
        super("주문에 사용된 메뉴는 삭제할 수 없습니다.");
    }
}