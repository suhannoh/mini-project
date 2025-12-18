package com.example.backend.error;
import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String msg,
        LocalDateTime timeStamp
) {
        public static ErrorResponse of (String code, String msg) {
            return new ErrorResponse(code, msg , LocalDateTime.now());
        }
}
