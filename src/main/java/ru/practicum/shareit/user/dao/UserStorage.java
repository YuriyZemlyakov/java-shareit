package ru.practicum.shareit.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.EditedUserFields;

import java.util.Collection;

public interface UserStorage extends JpaRepository<User,Long> {
}
