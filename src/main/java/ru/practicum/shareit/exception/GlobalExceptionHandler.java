package ru.practicum.shareit.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @Builder
    public record ErrorResponse(
            Instant timestamp,
            Integer status,
            String error,
            String message,
            String path) {
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCustomException(BaseException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getStatus().value()).body(ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(e.getStatus().value())
                .error(e.getStatus().getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }
}
