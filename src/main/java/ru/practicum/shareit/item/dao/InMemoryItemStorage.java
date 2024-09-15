package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemStorage implements ItemStorage {
    private Map<Long, Item> items = new HashMap<>();

    @Override
    public Item addItem(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(long itemId, EditItemRequestDto editedFields) {
        itemNotNullValidate(itemId);
        Item item = items.get(itemId);
        if (editedFields.getName() != null) {
            item.setName(editedFields.getName());
        }
        if (editedFields.getDescription() != null) {
            item.setDescription(editedFields.getDescription());
        }
        if (editedFields.getAvailable() != null) {
            item.setAvailable(editedFields.getAvailable());
        }
        return item;
    }

    @Override
    public Item getItem(long itemId) {
        itemNotNullValidate(itemId);
        return items.get(itemId);
    }

    @Override
    public void deleteItem(long itemId) {
        items.remove(itemId);
    }

    @Override
    public Collection<Item> searchItem(String text) {
        return items.values().stream()
                .filter(item -> (item.getAvailable() == true)
                        && (item.getName() != null && !text.isBlank()
                        && item.getName().toLowerCase().contains(text.toLowerCase()))
                        || (item.getDescription() != null && !text.isBlank()
                        && item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> getItemsByOwner(long ownerId) {
        return items.values().stream()
                .filter(item -> (item.getOwner() == ownerId))
                .collect(Collectors.toList());
    }

    private void itemNotNullValidate(long itemId) {
        if (!items.containsKey(itemId)) {
            throw new NotFoundException(String.format("Вещь с id {} не найдена", itemId));
        }
    }

    public Long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
