package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class UpdateItemDTO {
    String name;
    String description;
    Boolean available;
}
