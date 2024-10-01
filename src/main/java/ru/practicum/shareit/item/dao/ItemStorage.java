package ru.practicum.shareit.item.dao;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage extends JpaRepository<Item, Long> {
    @Query("select i from Item i join i.owner u where u.id  = :ownerId")
    Collection<Item> getItemsByOwner(long ownerId);

    @Query("select i from Item i where i.available = true and :text != '' and (lower(i.name) like %:text% or lower(i.description) like %:text%)")
    Collection<Item> searchItem(String text);

    @NotFound(action = NotFoundAction.IGNORE)
    Item getItemById(long itemId);
}
