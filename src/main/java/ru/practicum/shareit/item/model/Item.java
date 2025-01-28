package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Item {
     Long id;
     String name; // краткое название
     String description; // развёрнутое описание
     Boolean available; // статус о том, доступна или нет вещь для аренды
     Long owner; // владелец вещи
     Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос
}
