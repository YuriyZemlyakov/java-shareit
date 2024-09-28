package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;

import java.util.Collection;

public interface ItemStorage extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.available = true and (lower(i.name) like %:text% or lower(i.description) like %:text%)")
    Collection<Item> searchItem(String text);
}
