package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public final class CommentDTO {
    private Long id;

    @NotBlank(groups = CommentDTO.Create.class)
    private String text;

    private Long item;

    private String authorName;

    private LocalDateTime created;

    public interface Create {
    }
}
