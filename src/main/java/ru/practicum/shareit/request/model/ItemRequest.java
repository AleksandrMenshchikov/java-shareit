package ru.practicum.shareit.request.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ItemRequest {
    Long id;
    String description; // текст запроса, содержащий описание требуемой вещи
    Long requestor; // пользователь, создавший запрос
    Instant created; // дата и время создания запроса
}
