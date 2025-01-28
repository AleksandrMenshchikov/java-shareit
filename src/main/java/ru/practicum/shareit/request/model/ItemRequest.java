package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public final class ItemRequest {
    Long id;
    String description; // текст запроса, содержащий описание требуемой вещи
    Long requestor; // пользователь, создавший запрос
    Instant created; // дата и время создания запроса
}
