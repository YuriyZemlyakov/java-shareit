package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.dto.*;

import java.util.Collection;

public interface ItemService {
    OwnerItemDto getItem(long itemId);

    Collection<OwnerItemDto> getItemsByOwner(long ownerId);

    ItemDto addItem(long userId, ItemDto newItem);

    ItemDto editItem(long itemId, long userId, EditItemRequestDto editedFields);

    Collection<ItemDto> searchItem(String text);

    void deleteItem(long itemId);

    CommentResponseDto addComment(long userId, long itemId, CommentRequestDto requestDto);
}
