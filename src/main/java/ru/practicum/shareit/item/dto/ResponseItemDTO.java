package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.ResponseBookingDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseItemDTO {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long ownerId;

    private Long requestId;

    private List<CommentDTO> comments = new ArrayList<>();

    private ResponseBookingDTO lastBooking;

    private ResponseBookingDTO nextBooking;
}
