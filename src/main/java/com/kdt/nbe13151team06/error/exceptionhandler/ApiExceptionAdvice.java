package com.kdt.nbe13151team06.error.exceptionhandler;

import com.kdt.nbe13151team06.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({
            VALIDATION_ERROR.class,
            HttpMessageNotReadableException.class,
            //JSON이 깨졌거나, 타입이 안 맞거나, 필수 값이 null일 때
            MissingServletRequestParameterException.class,
            //?email=, ?keyword= 같은 필수 쿼리 파라미터가 빠졌을 때
            MethodArgumentTypeMismatchException.class,
            //숫자 자리에 문자가 들어오거나, 날짜 형식이 틀렸을 때
            HandlerMethodValidationException.class
            //@RequestParam, @PathVariable에 붙인 검증 규칙을 어길 때
    })
    public ResponseEntity<ApiError> handlerValidationError() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("VALIDATION_ERROR", "입력값을 확인해 주세요."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerBodyValidationError(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            // items[0].quantity 같은 경로는 items 로 줄인다
            String name = error.getField().split("[\\[.]")[0];
            fieldErrors.putIfAbsent(name, error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("VALIDATION_ERROR", "입력값을 확인해 주세요.", fieldErrors));
    }

    @ExceptionHandler(CATEGORY_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerCategoryNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다."));
    }

    @ExceptionHandler(MENU_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerMenuNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("MENU_NOT_FOUND", "메뉴를 찾을 수 없습니다."));
    }

    @ExceptionHandler(ORDER_NOT_FOUND.class)
    public ResponseEntity<ApiError> handlerOrderNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("ORDER_NOT_FOUND", "주문을 찾을 수 없습니다."));
    }

    @ExceptionHandler(CATEGORY_NAME_DUPLICATED.class)
    public ResponseEntity<ApiError> handlerCategoryNameDuplicated() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CATEGORY_NAME_DUPLICATED", "같은 이름의 카테고리가 이미 있습니다."));
    }

    @ExceptionHandler(CATEGORY_IN_USE.class)
    public ResponseEntity<ApiError> handlerCategoryInUse() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CATEGORY_IN_USE", "메뉴가 등록된 카테고리는 삭제할 수 없습니다."));
    }

    @ExceptionHandler(MENU_IN_USE.class)
    public ResponseEntity<ApiError> handlerMenuInUse() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("MENU_IN_USE", "주문에 사용된 메뉴는 삭제할 수 없습니다."));
    }

    @ExceptionHandler(ADDRESS_MISMATCH.class)
    public ResponseEntity<ApiError> handlerAddressMisMatch() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("ADDRESS_MISMATCH", "같은 이메일과 배송일의 기존 주문과 배송지가 다릅니다."));
    }

    @ExceptionHandler(ORDER_NOT_CANCELLABLE.class)
    public ResponseEntity<ApiError> handlerOrderNotCancellable() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("ORDER_NOT_CANCELLABLE", "배송 대상일 14시 전의 접수 상태 주문만 취소할 수 있습니다."));
    }

    @ExceptionHandler(MENU_UNAVAILABLE.class)
    public ResponseEntity<ApiError> handlerMenuUnavailable() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("MENU_UNAVAILABLE", "판매 중지된 메뉴가 포함되어 있습니다."));
    }

}