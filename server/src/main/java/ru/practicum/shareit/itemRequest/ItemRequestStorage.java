package ru.practicum.shareit.itemRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRequestStorage extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findByRequester_idEquals(long requesterId);
}
