package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {
    public static Item toNewItem(ItemDTO itemDTO, User user, Request request) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setIsAvailable(itemDTO.getAvailable());
        item.setOwner(user);
        item.setRequest(request);
        return item;
    }

    public static Item toItem(ItemDTO itemDTO, Long itemId, User owner, Request request) {
        Item item = new Item();
        item.setId(itemId);
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setIsAvailable(itemDTO.getAvailable());
        item.setOwner(owner);
        item.setRequest(request);
        return item;
    }

    public static ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setAvailable(item.getIsAvailable());
        itemDTO.setOwnerId(item.getOwner() != null ? item.getOwner().getId() : null);
        itemDTO.setRequestId(item.getRequest() != null ? item.getRequest().getId() : null);
        return itemDTO;
    }

    public static List<ItemDTO> toItemDTOList(List<Item> items) {
        return items.stream().map(ItemMapper::toItemDTO).collect(Collectors.toList());
    }

    public static ResponseItemDTO toResponseItemDTO(Item item, List<CommentDTO> comments) {
        ResponseItemDTO itemDTO = new ResponseItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setAvailable(item.getIsAvailable());
        itemDTO.setRequestId(item.getRequest() != null ? item.getRequest().getId() : null);
        itemDTO.setOwnerId(item.getOwner() != null ? item.getOwner().getId() : null);
        itemDTO.setComments(comments);
        return itemDTO;
    }
}
