package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.EditItemRequestDto;

import java.util.Collection;

public class ItemServiceImpl implements ItemService{
    @Override
    public ItemDto getItem(long itemId) {
        return null;
    }

    @Override
    public Collection<ItemDto> getAllItems() {
        return null;
    }

    @Override
    public ItemDto addItem(ItemDto newItem) {
        return null;
    }

    @Override
    public ItemDto editItem(long itemId, EditItemRequestDto editedFields) {

        return null;
    }

    @Override
    public Collection<ItemDto> searchItem(String text) {
        return null;
    }

    @Override
    public void deleteItem(long itemId) {

    }
}
