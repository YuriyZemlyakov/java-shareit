package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.model.dtoMappers.UserDtoMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public Collection<UserDto> getAllUsers() {
        return userStorage.getAllUsers().stream()
                .map(user -> UserDtoMapper.userToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long userId) {
        return UserDtoMapper.userToDto(userStorage.getUser(userId));
    }

    public UserDto addUser(UserDto user) {
//        validateUser(user);
        return UserDtoMapper.userToDto(userStorage.addUser(UserDtoMapper.dtoToUser(user)));
    }

    @Override
    public UserDto updateUser(long userId, EditedUserFields editedUserFields) {
//        validtateEditUserDto(editedUserFields);
        return UserDtoMapper.userToDto(userStorage.updateUser(userId, editedUserFields));
    }

    @Override
    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }

}
