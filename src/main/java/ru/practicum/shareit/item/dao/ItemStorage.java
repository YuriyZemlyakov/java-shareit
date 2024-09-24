package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;

import java.util.Collection;

public interface ItemStorage {
    Item addItem(Item item);

    Item updateItem(long itemId, EditItemRequestDto editedFields);

    Item getItem(long itemId);

    void deleteItem(long itemId);

    Collection<Item> searchItem(String text);

    Collection<Item> getItemsByOwner(long ownerId);
}
