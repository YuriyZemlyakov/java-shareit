package ru.practicum.shareit.item.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemReqResDto;
import ru.practicum.shareit.item.model.dto.OwnerItemDto;
import ru.practicum.shareit.user.model.User;


@Mapper(componentModel = "spring", uses = {User.class})
public interface ItemDtoMapper {

    @Mapping(source = "requestId", target = "request")
    Item dtoToItem(ItemDto dto);

    @Mapping(source = "request", target = "requestId")
    ItemDto itemToDto(Item item);

    @Mapping(source = "request", target = "requestId")
    OwnerItemDto itemToOwnerDto(Item item);

    @Mapping(source = "owner.id", target = "ownerId")
    ItemReqResDto itemToItemReqResDto(Item item);
}
