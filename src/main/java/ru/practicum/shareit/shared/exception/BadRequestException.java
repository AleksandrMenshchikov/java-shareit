package ru.practicum.shareit.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    private BadRequestException(String message, HttpStatus status) {
        super(message, status);
    }
}
