package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class Item {
    Long id;
    String name; // краткое название
    String description; // развёрнутое описание
    Boolean available; // статус о том, доступна или нет вещь для аренды
    Long owner; // владелец вещи
    Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос
}
