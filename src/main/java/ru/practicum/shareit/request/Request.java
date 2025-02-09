package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

@Getter
@Setter
@Entity
@Table(name = "requests")
public final class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String description; // текст запроса, содержащий описание требуемой вещи

    @ManyToOne
    User requestor; // пользователь, создавший запрос
}
