package ru.practicum.shareit.user.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.EditedUserFields;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Data
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User getUser(long userId) {
        return users.get(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User addUser(User user) {
        checkEmail(user.getEmail());
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(long userId, EditedUserFields editedUserFields) {
        if (editedUserFields.getName() != null) {
            users.get(userId).setName(editedUserFields.getName());
        }
        if (editedUserFields.getEmail() != null) {
            checkEmail(editedUserFields.getEmail());
            users.get(userId).setEmail(editedUserFields.getEmail());
        }
        return users.get(userId);
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    private Long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void checkEmail(String email) {
        boolean isNotUnique = users.values().stream()
                .map(u -> u.getEmail())
                .anyMatch(s -> s.equals(email));
        if (isNotUnique) {
            throw new ConflictException(String.format("Пользователь с email {} уже зарегистрирован", email));
        }

    }
}
