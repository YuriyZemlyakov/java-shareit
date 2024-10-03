package ru.practicum.shareit.item.model.dtoMapper;

import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.OwnerItemDto;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;


@Mapper(componentModel = "spring", uses = {User.class})
public interface ItemDtoMapper {

    Item dtoToItem(ItemDto dto);

    ItemDto itemToDto(Item item);

    OwnerItemDto itemToOwnerDto(Item item);
}
