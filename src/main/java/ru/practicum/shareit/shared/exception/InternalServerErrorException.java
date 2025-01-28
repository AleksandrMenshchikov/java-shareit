package ru.practicum.shareit.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private InternalServerErrorException(String message, HttpStatus status) {
        super(message, status);
    }
}
