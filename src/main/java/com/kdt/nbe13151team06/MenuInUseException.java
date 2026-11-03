package com.kdt.nbe13151team06;

public class MenuInUseException extends RuntimeException {
    public MenuInUseException() {
        super("주문에 사용된 메뉴는 삭제할 수 없습니다.");
    }
}