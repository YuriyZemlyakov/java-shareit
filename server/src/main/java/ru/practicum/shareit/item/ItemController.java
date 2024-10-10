package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.dto.*;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody ItemDto item) {
        return itemService.addItem(userId, item);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable long itemId,
                                         @RequestBody CommentRequestDto requestDto) {
        return itemService.addComment(userId, itemId, requestDto);
    }

    @GetMapping("/{itemId}")
    public OwnerItemDto getItemById(@PathVariable long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public Collection<OwnerItemDto> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getItemsByOwner(ownerId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchItem(text);
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable long itemId, @RequestBody EditItemRequestDto editItemRequestDto,
                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.editItem(itemId, userId, editItemRequestDto);
    }
}
