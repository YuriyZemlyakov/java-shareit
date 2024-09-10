package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserStorage {
    User getUser(long userId);
    Collection<User> getAllUsers();
    User addUser(User user);
    User updateUser(User editedUser);
    void deleteUser(long userId);
}
