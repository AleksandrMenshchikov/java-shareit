package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenRequestException extends BaseException {
    public ForbiddenRequestException(String message) {
        this(message, HttpStatus.FORBIDDEN);
    }

    private ForbiddenRequestException(String message, HttpStatus status) {
        super(message, status);
    }
}