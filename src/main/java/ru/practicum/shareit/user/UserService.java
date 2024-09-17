package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.model.dtoMappers.UserDtoMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<UserDto> getAllUsers() {
        return userStorage.getAllUsers().stream()
                .map(user -> UserDtoMapper.userToDto(user))
                .collect(Collectors.toList());
    }

    public UserDto getUser(long userId) {
        return UserDtoMapper.userToDto(userStorage.getUser(userId));
    }

    public UserDto addUser(UserDto user) {
        validateUser(user);
        return UserDtoMapper.userToDto(userStorage.addUser(UserDtoMapper.dtoToUser(user)));
    }

    public UserDto updateUser(long userId, EditedUserFields editedUserFields) {
        validtateEditUserDto(editedUserFields);
        return UserDtoMapper.userToDto(userStorage.updateUser(userId, editedUserFields));
    }

    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }

    private void validateUser(UserDto userDto) {
        if (userDto.getName().isBlank()) {
            throw new ValidationException("Имя пользователя должно быть заполнено");
        }
        if (userDto.getEmail().isBlank()) {
            throw new ValidationException("email должен быть заполнен");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат email");
        }
    }

    private void validtateEditUserDto(EditedUserFields editedUserFields) {
        if (editedUserFields.getName() != null && editedUserFields.getName().isBlank()) {
            throw new ValidationException("Имя пользователя должно быть заполнено");
        }
        if (editedUserFields.getEmail() != null && editedUserFields.getEmail().isBlank()) {
            throw new ValidationException("email должен быть заполнен");
        }
        if (editedUserFields.getEmail() != null && !editedUserFields.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат email");
        }
    }

}
