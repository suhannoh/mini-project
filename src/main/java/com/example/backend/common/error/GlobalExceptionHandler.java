package com.example.backend.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// controller에서 예외가 발생 후 try/catch 문이 없으면 Advice를 찾아서 옴
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 내가 정의한 비즈니스 예외 처리 (403, 404)
    // BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorCode ec = e.getErrorCode();
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code , e.getMessage()));
    }

    // 400 / 파라미터 or 입력값 이상
    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e) {
        ErrorCode ec = ErrorCode.BAD_REQUEST;
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code , e.getMessage()));
    }

    // 500 / 예상 못 한 에러 (외 모든 에러)
    // Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception e) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code , e.getMessage()));
    }
}
