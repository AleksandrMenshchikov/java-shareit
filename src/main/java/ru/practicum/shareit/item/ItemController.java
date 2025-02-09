package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;

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

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@Validated(CommentDTO.Create.class) @RequestBody final CommentDTO commentDTO,
                                    @RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable final Long itemId) {
        return itemService.createComment(commentDTO, userId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO updateItem(@Validated(ItemDTO.Update.class) @RequestBody final ItemDTO itemDTO,
                              @RequestHeader("X-Sharer-User-Id") final Long userId,
                              @PathVariable final Long itemId) {
        return itemService.updateItem(itemDTO, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseItemDTO getItem(@RequestHeader("X-Sharer-User-Id") final Long userId,
                                   @PathVariable final Long itemId) {
        return itemService.getItem(userId, itemId);
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
