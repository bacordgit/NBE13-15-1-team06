package com.kdt.nbe13151team06.error;

public class CATEGORY_IN_USE extends RuntimeException {
    public CATEGORY_IN_USE(String message) {
        super("메뉴가 등록된 카테고리는 삭제할 수 없습니다.");
    }
}
