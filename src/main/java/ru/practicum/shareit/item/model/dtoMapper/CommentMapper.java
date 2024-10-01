package ru.practicum.shareit.item.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.dto.CommentRequestDto;
import ru.practicum.shareit.item.model.dto.CommentResponseDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment requestDtoToEntity(CommentRequestDto dto);

    @Mapping(source = "author.name", target = "authorName")
    CommentResponseDto entityToResponsetDto(Comment comment);
}
