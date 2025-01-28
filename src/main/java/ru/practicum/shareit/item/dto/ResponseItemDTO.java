package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class ResponseItemDTO {
    Long id;
    String name;
    String description;
    Boolean available;
    Long owner;
    Long request;
}
