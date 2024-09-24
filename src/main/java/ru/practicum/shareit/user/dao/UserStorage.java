package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.EditedUserFields;

import java.util.Collection;

public interface UserStorage {
    User getUser(long userId);

    Collection<User> getAllUsers();

    User addUser(User user);

    User updateUser(long userId, EditedUserFields editedUserfields);

    void deleteUser(long userId);
}
