package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

public interface CommentStorage extends JpaRepository<Comment, Long> {
    @Query("Select c from Comment c join c.item i join i.owner o where o.id = ?1 and i.id = ?2")
    public Collection<Comment> findAllByAuthorAndItem(long authorId, long itemId);
}
