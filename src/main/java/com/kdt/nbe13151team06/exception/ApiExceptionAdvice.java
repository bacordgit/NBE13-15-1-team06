package com.kdt.nbe13151team06.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerValidationError() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("BAD_REQUEST", "입력값을 확인해 주세요."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerCategoryNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "카테고리를 찾을 수 없습니다"));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerMenuNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "메뉴를 찾을 수 없습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerOrderNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "주문을 찾을 수 없습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerCategoryNameDuplicated() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "같은 이름의 카데고리가 이미 있습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerCategoryInUse() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "메뉴가 등록된 카테고리는 삭제할 수 없습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerMenuInUse() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "주문에 사용된 메뉴는 삭제할 수 없습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerAddressMisMatch() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "같은 이메일과 배송일의 기존 주문과 배송지가 다릅니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerOrderNotCancellable() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "배송 대상일 14시 전의 접수 상태 주문만 취소할 수 있습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handlerMenuUnavailable() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ApiError("REQUEST_TIMEOUT", "판매 중지된 메뉴가 포함되어 있습니다."));
    }

}
