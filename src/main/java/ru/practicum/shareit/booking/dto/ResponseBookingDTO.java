package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public final class ResponseBookingDTO {
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private ItemIdName item;

    private BookerId booker;

    private BookingStatus status;
}
