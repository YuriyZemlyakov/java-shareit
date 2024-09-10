package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;

import java.util.Collection;
import java.util.List;
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
        return UserDtoMapper.userToDto( userStorage.addUser(UserDtoMapper.dtoToUser(user)));
    }
    public UserDto updateUser(UserDto editedUser) {
        return UserDtoMapper.userToDto(userStorage.updateUser(UserDtoMapper.dtoToUser(editedUser)));
    }

}
