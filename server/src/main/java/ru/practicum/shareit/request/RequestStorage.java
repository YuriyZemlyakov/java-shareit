package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RequestStorage extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findByRequester_idEquals(long requesterId);
}
