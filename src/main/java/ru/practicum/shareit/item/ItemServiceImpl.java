package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.user.dao.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto getItem(long itemId) {
        return ItemDtoMapper.itemToDto(itemStorage.getItem(itemId));
    }


    @Override
    public Collection<ItemDto> getItemsByOwner(long ownerId) {
        return itemStorage.getItemsByOwner(ownerId).stream()
                .map(item -> ItemDtoMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(long userId, ItemDto newItem) {
        validateItemDto(newItem);
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException(String.format("Пользователь с id {} не найден", userId));
        }
        Item item = ItemDtoMapper.dtoToItem(newItem);
        item.setOwner(userId);
        return ItemDtoMapper.itemToDto(itemStorage.addItem(item));
    }

    @Override
    public ItemDto editItem(long itemId, long userId, EditItemRequestDto editedFields) {
        validateItemDto(editedFields);
        if (userId != itemStorage.getItem(itemId).getOwner()) {
            throw new NotFoundException(String.format("У пользовател {} нет вещи {}", userId, itemId));
        }

        return ItemDtoMapper.itemToDto(itemStorage.updateItem(itemId, editedFields));
    }

    @Override
    public Collection<ItemDto> searchItem(String text) {
        return itemStorage.searchItem(text).stream()
                .map(item -> ItemDtoMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(long itemId) {
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
