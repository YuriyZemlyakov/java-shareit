package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;

@Mapper
public interface RequestMapper {
    @Mapping(source = "requester.id", target = "requesterId")
    RequestResponseDto entityToDto(ItemRequest request);

    ItemRequest dtoToEntity(RequestRequestDto dto);
}
