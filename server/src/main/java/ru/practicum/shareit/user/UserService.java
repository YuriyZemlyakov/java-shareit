package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getAllUsers();

    UserDto getUser(long userId);

    UserDto addUser(UserDto user);

    UserDto updateUser(long userId, EditedUserFields editedUserFields);

    void deleteUser(long userId);
}
