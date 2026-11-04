package com.kdt.nbe13151team06.error.exceptionhandler;

import com.kdt.nbe13151team06.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(VALIDATION_ERROR.class)
    public ResponseEntity<ApiError> handlerValidationError() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("BAD_REQUEST", "입력값을 확인해 주세요."));
    }

    @ExceptionHandler(CATEGORY_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerCategoryNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "카테고리를 찾을 수 없습니다"));
    }

    @ExceptionHandler(MENU_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerMenuNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "메뉴를 찾을 수 없습니다."));
    }

    @ExceptionHandler(ORDER_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerOrderNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", "주문을 찾을 수 없습니다."));
    }

    @ExceptionHandler(CATEGORY_NAME_DUPLICATED.class)
    public ResponseEntity<ApiError> handlerCategoryNameDuplicated() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "같은 이름의 카데고리가 이미 있습니다."));
    }

    @ExceptionHandler(CATEGORY_IN_USE.class)
    public ResponseEntity<ApiError> handlerCategoryInUse() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "메뉴가 등록된 카테고리는 삭제할 수 없습니다."));
    }

    @ExceptionHandler(MENU_IN_USE.class)
    public ResponseEntity<ApiError> handlerMenuInUse() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "주문에 사용된 메뉴는 삭제할 수 없습니다."));
    }

    @ExceptionHandler(ADDRESS_MISMATCH.class)
    public ResponseEntity<ApiError> handlerAddressMisMatch() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "같은 이메일과 배송일의 기존 주문과 배송지가 다릅니다."));
    }

    @ExceptionHandler(ORDER_NOT_CANCELLABLE.class)
    public ResponseEntity<ApiError> handlerOrderNotCancellable() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "배송 대상일 14시 전의 접수 상태 주문만 취소할 수 있습니다."));
    }

    @ExceptionHandler(MENU_UNAVAILABLE.class)
    public ResponseEntity<ApiError> handlerMenuUnavailable() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", "판매 중지된 메뉴가 포함되어 있습니다."));
    }

}
