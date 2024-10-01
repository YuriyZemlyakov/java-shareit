package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

public interface CommentStorage extends JpaRepository<Comment,Long> {
    @Query(value = "select с.* from comments c join users u on c.user_id = u.id join items i on c.item_id = i.id " +
            "where u.id = :authorId and i.id = :itemId",
            nativeQuery = true)
    public Collection<Comment> findAllByAuthorAndItem(long authorId, long itemId);
}
