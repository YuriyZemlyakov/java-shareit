package ru.practicum.shareit.user.dao;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

public interface UserStorage extends JpaRepository<User, Long> {
    @NotFound(action = NotFoundAction.IGNORE)
    User getUserById(long userId);
}
