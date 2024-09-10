package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.EditItemRequestDto;

import java.util.Collection;

public interface ItemService  {
    ItemDto getItem(long itemId);
    Collection<ItemDto> getAllItems();
    ItemDto addItem(ItemDto newItem);
    ItemDto editItem(long itemId, EditItemRequestDto editedFields);
    Collection<ItemDto> searchItem(String text);
    void deleteItem(long itemId);
}
