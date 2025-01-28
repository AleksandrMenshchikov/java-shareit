package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CreateItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;
import ru.practicum.shareit.item.dto.UpdateItemDTO;
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

    public ResponseItemDTO createItem(CreateItemDTO createItemDTO, Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Long id = itemRepository.generateId();
        Item item = ItemMapper.toItem(createItemDTO, id, userId);
        itemRepository.insert(item, id);
        return ItemMapper.toResponseItemDTO(item);
    }

    public ResponseItemDTO updateItem(UpdateItemDTO updateItemDTO, Long userId, Long itemId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Item oldItem = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", itemId)));

        if (!oldItem.getOwner().equals(userId)) {
            throw new BadRequestException(String.format("User with id '%d' is not owned by '%d'", userId, itemId));
        }

        if (updateItemDTO.getName() == null || updateItemDTO.getName().isBlank()) {
            updateItemDTO.setName(oldItem.getName());
        }

        if (updateItemDTO.getDescription() == null || updateItemDTO.getDescription().isBlank()) {
            updateItemDTO.setDescription(oldItem.getDescription());
        }

        if (updateItemDTO.getAvailable() == null) {
            updateItemDTO.setAvailable(oldItem.getAvailable());
        }

        Item item = ItemMapper.toItem(updateItemDTO, itemId, userId, oldItem.getRequest());
        itemRepository.updateById(itemId, item);
        return ItemMapper.toResponseItemDTO(item);
    }

    public ResponseItemDTO getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Item with id '%d' not found", itemId)));
        return ItemMapper.toResponseItemDTO(item);
    }

    public List<ResponseItemDTO> getItems(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        List<Item> list = itemRepository.getData().values().stream().filter((item) -> item.getOwner().equals(userId)).toList();
        return ItemMapper.toResponseItemDTOList(list);
    }

    public List<ResponseItemDTO> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }

        List<Item> list = itemRepository.getData().values().stream()
                .filter((item) -> item.getAvailable() && item.getName().toUpperCase().contains(text.toUpperCase())
                                  || item.getDescription().toUpperCase().contains(text.toUpperCase())).toList();
        return ItemMapper.toResponseItemDTOList(list);
    }
}
