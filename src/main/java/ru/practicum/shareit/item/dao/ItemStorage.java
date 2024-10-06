package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage extends JpaRepository<Item, Long> {
    Collection<Item> findByOwner_IdEquals(long ownerId);

    @Query("select i from Item i where i.available = true and :text != '' and (lower(i.name) like %:text% or lower(i.description) like %:text%)")
    Collection<Item> searchItem(String text);

    //    @NotFound(action = NotFoundAction.IGNORE)
    Optional<Item> findById(long itemId);
}
