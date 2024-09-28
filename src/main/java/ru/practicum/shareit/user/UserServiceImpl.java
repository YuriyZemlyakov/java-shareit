package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;
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
        return userStorage.findAll().stream()
                .map(user -> UserDtoMapper.userToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long userId) {
        return UserDtoMapper.userToDto(userStorage.getReferenceById(userId));
    }

    public UserDto addUser(UserDto user) {
        return UserDtoMapper.userToDto(userStorage.save(UserDtoMapper.dtoToUser(user)));
    }

    @Override
    public UserDto updateUser(long userId, EditedUserFields editedUserFields) {
        User editedUser = userStorage.getReferenceById(userId);
        if (editedUserFields.getEmail() != null) {
            editedUser.setEmail(editedUserFields.getEmail());
        }
        if (editedUserFields.getName() != null) {
            editedUser.setName(editedUserFields.getName());
        }
        return UserDtoMapper.userToDto(userStorage.save(editedUser));
    }

    @Override
    public void deleteUser(long userId) {
        userStorage.deleteById(userId);
    }

}
