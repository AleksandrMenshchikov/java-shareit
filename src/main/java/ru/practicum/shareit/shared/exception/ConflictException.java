package ru.practicum.shareit.shared.exception;

import org.springframework.http.HttpStatus;

public final class ConflictException extends BaseException {
    public ConflictException(String message) {
        this(message, HttpStatus.CONFLICT);
    }

    private ConflictException(String message, HttpStatus status) {
        super(message, status);
    }
}
