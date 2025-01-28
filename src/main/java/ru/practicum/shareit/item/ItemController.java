package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDTO;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public final class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO createItem(@Validated(ItemDTO.Create.class) @RequestBody final ItemDTO itemDTO,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.createItem(itemDTO, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO updateItem(@Validated(ItemDTO.Update.class) @RequestBody final ItemDTO itemDTO,
                                      @RequestHeader("X-Sharer-User-Id") final Long userId,
                                      @PathVariable final Long itemId) {
        return itemService.updateItem(itemDTO, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDTO getItem(@PathVariable final Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDTO> getItems(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDTO> search(@RequestParam final String text) {
        return itemService.search(text);
    }
}
