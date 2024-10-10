package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
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
    private final UserDtoMapper mapper;

    @Override
    public Collection<UserDto> getAllUsers() {
        return userStorage.findAll().stream()
                .map(user -> mapper.userToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long userId) {
        return mapper.userToDto(userStorage.findById(userId).orElseThrow(() ->new NotFoundException("Id не найден")));
    }

    public UserDto addUser(UserDto user) {
        return mapper.userToDto(userStorage.save(mapper.dtoToUser(user)));
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
        return mapper.userToDto(userStorage.save(editedUser));
    }

    @Override
    public void deleteUser(long userId) {
        userStorage.deleteById(userId);
    }

}
