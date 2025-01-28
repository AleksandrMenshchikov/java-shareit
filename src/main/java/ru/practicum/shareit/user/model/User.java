package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class User {
    Long id;
    String name; // имя или логин пользователя
    String email; // уникальный адрес электронной почты
}
