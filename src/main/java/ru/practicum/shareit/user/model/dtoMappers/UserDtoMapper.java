package ru.practicum.shareit.user.model.dtoMappers;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

@Mapper
public interface UserDtoMapper {
    public UserDto userToDto(User user);

    public User dtoToUser(UserDto dto);

}
