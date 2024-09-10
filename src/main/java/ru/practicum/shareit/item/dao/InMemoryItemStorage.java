package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.EditItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryItemStorage implements ItemStorage {
    private Map<Long, Item> items = new HashMap<>();

    @Override
    public Item addItem(Item item) {
        return null;
    }

    @Override
    public Item updateItem(long itemId, EditItemRequestDto editedFields) {
        if (!items.containsKey(itemId)) {
            throw new NotFoundException(String.format("Id {} не найден", itemId));
        }
        Item item = items.get(itemId);
        if (editedFields.getName() != null) {
            item.setName(editedFields.getName());
        }
        if (editedFields.getDescription() != null) {
            item.setDescription(editedFields.getDescription());
        }
        if (editedFields.isAvailable()) {
            item.setAvailable(editedFields.isAvailable());
        }
    }

    @Override
    public Item getItem(long itemId) {
        return null;
    }

    @Override
    public void deleteItem(long itemId) {

    }

    @Override
    public Collection<Item> searchItem(String text) {
        return null;
    }
}
