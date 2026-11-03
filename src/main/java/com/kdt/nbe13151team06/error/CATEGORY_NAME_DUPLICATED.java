package com.kdt.nbe13151team06.error;

public class CATEGORY_NAME_DUPLICATED extends RuntimeException {
    public CATEGORY_NAME_DUPLICATED(String message) {
        super("같은 이름의 카테고리가 이미 있습니다.");
    }
}
