package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.user.User;

@Getter
@Setter
@Entity
@Table(name = "items")
public final class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // краткое название

    @Column(nullable = false)
    private String description; // развёрнутое описание

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable; // статус о том, доступна или нет вещь для аренды

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User owner; // владелец вещи

    @ManyToOne(fetch = FetchType.LAZY)
    private Request request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос
}
