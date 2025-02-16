package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateBookingDTO {
    @NotNull
    @FutureOrPresent
    private LocalDateTime start; // дата и время начала бронирования

    @NotNull
    @FutureOrPresent
    private LocalDateTime end; // дата и время конца бронирования

    @NotNull
    @Positive
    private Long itemId;
}
