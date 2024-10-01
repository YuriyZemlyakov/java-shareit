package ru.practicum.shareit.item.model.dtoMapper;

import lombok.AllArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.OwnerItemDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.dao.UserStorage;

@AllArgsConstructor
public class ItemDtoMapper {
    private final UserStorage storage;
    public static Item dtoToItem(ItemDto dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setOwner(dto.getOwner());
        item.setRequest(dto.getRequest());
        item.setAvailable(dto.getAvailable());
        return item;
    }

    public static ItemDto itemToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setOwner(item.getOwner());
        dto.setRequest(item.getRequest());
        dto.setAvailable(item.getAvailable());
        return dto;
    }
    public static OwnerItemDto itemToOwnerDto(Item item) {
        OwnerItemDto dto = new OwnerItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setOwner(item.getOwner());
        dto.setRequest(item.getRequest());
        dto.setAvailable(item.getAvailable());
        return dto;
    }
}
