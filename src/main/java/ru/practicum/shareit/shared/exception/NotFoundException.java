package ru.practicum.shareit.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        this(message, HttpStatus.NOT_FOUND);
    }

    private NotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
