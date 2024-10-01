package ru.practicum.shareit.item.model.dtoMapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.dto.CommentRequestDto;
import ru.practicum.shareit.item.model.dto.CommentResponseDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment requestDtoToEntity(CommentRequestDto dto);
    CommentResponseDto entityToResponsetDto(Comment comment);
}
