package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;
import ru.practicum.shareit.item.dto.UpdateItemDTO;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public final class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseItemDTO createItem(@Valid @RequestBody final CreateItemDTO createItemDTO,
                                      @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.createItem(createItemDTO, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseItemDTO updateItem(@Valid @RequestBody final UpdateItemDTO updateItemDTO,
                                      @RequestHeader("X-Sharer-User-Id") final Long userId,
                                      @PathVariable final Long itemId) {
        return itemService.updateItem(updateItemDTO, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseItemDTO getItem(@PathVariable final Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ResponseItemDTO> getItems(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ResponseItemDTO> search(@RequestParam final String text) {
        return itemService.search(text);
    }
}
