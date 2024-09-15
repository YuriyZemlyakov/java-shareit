package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

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
        validateItemDto(item);
        headerNotNullValidate(userId);
        return itemService.addItem(userId, item);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public Collection<ItemDto> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getItemsByOwner(ownerId);
    }

    @GetMapping("/search")
    Collection<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchItem(text);
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable long itemId, @RequestBody EditItemRequestDto editItemRequestDto,
                            @RequestHeader("X-Sharer-User-Id") long userId) {
        validateItemDto(editItemRequestDto);
        headerNotNullValidate(userId);
        return itemService.editItem(itemId, userId, editItemRequestDto);
    }

    private void headerNotNullValidate(long userId) {
        if (userId == 0) {
            throw new NotFoundException(String.format("Не заполнен заголовок X-Sharer-User-Id"));
        }
    }

    private void validateItemDto(ItemDto itemDto) {
        if (itemDto.getName().isBlank()) {
            throw new ValidationException("Поле name должно быть заполнено");
        }
        if (itemDto.getDescription().isBlank()) {
            throw new ValidationException("Поле description должно быть заполнено");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Поле available должно быть заполнено");
        }
    }

    private void validateItemDto(EditItemRequestDto editItemRequestDto) {
        if (editItemRequestDto.getName() != null && editItemRequestDto.getName().isBlank()) {
            throw new ValidationException("Поле name должно быть заполнено");
        }
        if (editItemRequestDto.getDescription() != null && editItemRequestDto.getDescription().isBlank()) {
            throw new ValidationException("Поле description должно быть заполнено");
        }
        if (editItemRequestDto.getAvailable() != null && editItemRequestDto.getAvailable() == null) {
            throw new ValidationException("Поле available должно быть заполнено");
        }
    }

}
