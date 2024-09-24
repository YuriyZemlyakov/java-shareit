package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.dto.EditItemRequestDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto getItem(long itemId);

    Collection<ItemDto> getItemsByOwner(long ownerId);

    ItemDto addItem(long userId, ItemDto newItem);

    ItemDto editItem(long itemId, long userId, EditItemRequestDto editedFields);

    Collection<ItemDto> searchItem(String text);

    void deleteItem(long itemId);
}
