package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CreateItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;
import ru.practicum.shareit.item.dto.UpdateItemDTO;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public final class ItemMapper {
    public static Item toItem(CreateItemDTO createItemDTO, Long id, Long userId) {
        return Item.builder()
                .id(id)
                .name(createItemDTO.getName())
                .description(createItemDTO.getDescription())
                .available(createItemDTO.getAvailable())
                .owner(userId)
                .request(createItemDTO.getRequest())
                .build();
    }

    public static Item toItem(UpdateItemDTO updateItemDTO, Long id, Long userId, Long requestorId) {
        return Item.builder()
                .id(id)
                .name(updateItemDTO.getName())
                .description(updateItemDTO.getDescription())
                .available(updateItemDTO.getAvailable())
                .owner(userId)
                .request(requestorId)
                .build();
    }

    public static ResponseItemDTO toResponseItemDTO(Item item) {
        return ResponseItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .request(item.getRequest())
                .build();
    }

    public static List<ResponseItemDTO> toResponseItemDTOList(List<Item> items) {
        return items.stream().map((ItemMapper::toResponseItemDTO)).toList();
    }
}
