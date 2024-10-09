package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface RequestStorage extends JpaRepository<Request,Long> {
    Collection<Request> findByRequester_idEquals(long requesterId);
}
