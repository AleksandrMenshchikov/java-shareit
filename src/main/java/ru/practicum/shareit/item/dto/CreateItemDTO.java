package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class CreateItemDTO {
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    Boolean available;
    @Positive
    Long request;
}
