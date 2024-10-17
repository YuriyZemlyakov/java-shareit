package ru.practicum.shareit.itemRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;

@Mapper
public interface ItemRequestMapper {
    @Mapping(source = "requester.id", target = "requesterId")
    RequestResponseDto entityToDto(ItemRequest itemRequest);

    ItemRequest dtoToEntity(RequestRequestDto dto);
}
