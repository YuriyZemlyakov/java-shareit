package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.EditItemRequestDto;
import ru.practicum.shareit.item.model.Item;

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
    public ItemDto addItem(@RequestBody ItemDto item) {
       return itemService.addItem(item);
    }
    @GetMapping
    public Collection<ItemDto> getAllItems() {
        return itemService.getAllItems();
    }
    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable long itemId) {
        return itemService.getItem(itemId);
    }
    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable long itemId, @RequestBody EditItemRequestDto requestDto) {
        return itemService.editItem(itemId, requestDto);
    }

}
