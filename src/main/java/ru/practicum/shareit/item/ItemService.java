package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.shared.exception.BadRequestException;
import ru.practicum.shareit.shared.exception.NotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemDTO createItem(ItemDTO itemDTO, Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Long id = itemRepository.generateId();
        itemDTO.setId(id);
        itemDTO.setOwner(userId);
        Item item = ItemMapper.toItem(itemDTO);
        itemRepository.insert(item, id);
        return ItemMapper.toItemDTO(item);
    }

    public ItemDTO updateItem(ItemDTO itemDTO, Long userId, Long itemId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Item oldItem = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", itemId)));

        if (!oldItem.getOwner().equals(userId)) {
            throw new BadRequestException(String.format("User with id '%d' is not owned by '%d'", userId, itemId));
        }

        if (itemDTO.getName() == null || itemDTO.getName().isBlank()) {
            itemDTO.setName(oldItem.getName());
        }

        if (itemDTO.getDescription() == null || itemDTO.getDescription().isBlank()) {
            itemDTO.setDescription(oldItem.getDescription());
        }

        if (itemDTO.getAvailable() == null) {
            itemDTO.setAvailable(oldItem.getAvailable());
        }

        itemDTO.setId(oldItem.getId());
        itemDTO.setOwner(oldItem.getOwner());
        itemDTO.setRequest(oldItem.getRequest());
        Item item = ItemMapper.toItem(itemDTO);
        itemRepository.updateById(itemId, item);
        return ItemMapper.toItemDTO(item);
    }

    public ItemDTO getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Item with id '%d' not found", itemId)));
        return ItemMapper.toItemDTO(item);
    }

    public List<ItemDTO> getItems(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        List<Item> list = itemRepository.getData().values().stream().filter((item) -> item.getOwner().equals(userId)).toList();
        return ItemMapper.toResponseItemDTOList(list);
    }

    public List<ItemDTO> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }

        List<Item> list = itemRepository.getData().values().stream()
                .filter((item) -> item.getAvailable() && item.getName().toUpperCase().contains(text.toUpperCase())
                                  || item.getDescription().toUpperCase().contains(text.toUpperCase())).toList();
        return ItemMapper.toResponseItemDTOList(list);
    }
}
