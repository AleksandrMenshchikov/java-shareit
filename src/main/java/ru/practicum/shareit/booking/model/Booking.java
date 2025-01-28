package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.Instant;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Booking {
    Long id;
    Instant start; // дата и время начала бронирования
    Instant end; // дата и время конца бронирования
    Long item; // вещь, которую пользователь бронирует
    Long booker; // пользователь, который осуществляет бронирование
    BookingStatus status;
}
