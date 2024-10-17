package ru.practicum.shareit.item;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingStorage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.CommentStorage;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.*;
import ru.practicum.shareit.item.model.dtoMapper.CommentMapper;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;
    private final CommentMapper commentMapper;
    private final ItemDtoMapper itemDtoMapper;

    @Override
    public OwnerItemDto getItem(long itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> {
            throw new NotFoundException("Вещь не найдена");
        });
        long ownerId = item.getOwner().getId();
        Collection<CommentResponseDto> comments = getComments(ownerId, itemId);
        OwnerItemDto ownerItemDto = itemDtoMapper.itemToOwnerDto(item);
        ownerItemDto.setComments(comments);
        return ownerItemDto;
    }


    @Override
    public Collection<OwnerItemDto> getItemsByOwner(long ownerId) {
        return itemStorage.findByOwner_IdEquals(ownerId).stream()
                .map(item -> itemDtoMapper.itemToOwnerDto(item))
                .peek(ownerItemDto -> {
                    long itemId = ownerItemDto.getId();
                    ownerItemDto.setLastBooking(bookingStorage.findLastBookingDate(itemId));
                    ownerItemDto.setNextBooking(bookingStorage.findNextBookingDate(itemId));
                    Collection<CommentResponseDto> comments = getComments(ownerId, itemId);
                    ownerItemDto.setComments(comments);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDto addItem(long userId, ItemDto newItem) {
        Objects.requireNonNull(newItem, "Cannot add item, value is null");
        User owner = userStorage.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Пользователь не найден");
        });

        Item item = itemDtoMapper.dtoToItem(newItem);
        item.setOwner(owner);
        return itemDtoMapper.itemToDto(itemStorage.save(item));
    }

    @Override
    public ItemDto editItem(long itemId, long userId, EditItemRequestDto editedFields) {
        validateItemDto(editedFields);
        Item editedItem = itemStorage.getReferenceById(itemId);
        if (userId != editedItem.getOwner().getId()) {
            throw new NotFoundException(String.format("У пользовател {} нет вещи {}", userId, itemId));
        }
        if (editedFields.getName() != null) {
            editedItem.setName(editedFields.getName());
        }
        if (editedFields.getDescription() != null) {
            editedItem.setDescription(editedFields.getDescription());
        }
        if (editedFields.getAvailable() != null) {
            editedItem.setAvailable(editedFields.getAvailable());
        }
        return itemDtoMapper.itemToDto(itemStorage.save(editedItem));
    }

    @Override
    public Collection<ItemDto> searchItem(String text) {
        return itemStorage.searchItem(text.toLowerCase()).stream()
                .map(item -> itemDtoMapper.itemToDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto addComment(long userId, long itemId, CommentRequestDto requestDto) {
        checkAccessToComment(userId, itemId);
        Comment comment = commentMapper.requestDtoToEntity(requestDto);
        comment.setItem(itemStorage.getReferenceById(itemId));
        comment.setAuthor(userStorage.getReferenceById(userId));
        return commentMapper.entityToResponsetDto(commentStorage.save(comment));
    }

    @Override
    public void deleteItem(long itemId) {
        itemStorage.deleteById(itemId);
    }

    private void validateItemDto(EditItemRequestDto editItemRequestDto) {
        if (editItemRequestDto.getName() != null && editItemRequestDto.getName().isBlank()) {
            throw new ValidationException("Поле name должно быть заполнено");
        }
        if (editItemRequestDto.getDescription() != null && editItemRequestDto.getDescription().isBlank()) {
            throw new ValidationException("Поле description должно быть заполнено");
        }
        if (editItemRequestDto.getAvailable() != null && editItemRequestDto.getAvailable() == null) {
            throw new ValidationException("Поле available должно быть заполнено");
        }
    }

    private void checkAccessToComment(long userId, long itemId) {
        boolean canComment = bookingStorage.getBookingsByItem(itemId).stream()
                .filter(booking -> (booking.getBooker().getId() == userId && booking.getEnd()
                        .isBefore(LocalDateTime.now())))
                .findFirst().isPresent();
        if (!canComment) {
            bookingStorage.getBookingsByItem(itemId);
            throw new ValidationException(String.format("Пользователь %s не брал в аренду вещь %s, поэтому не имеет" +
                    "право писать комментарий", userId, itemId));
        }
    }

    private Collection<CommentResponseDto> getComments(long userId, long itemId) {
        return commentStorage.findAllByAuthorAndItem(userId, itemId)
                .stream()
                .map(comment -> commentMapper.entityToResponsetDto(comment))
                .collect(Collectors.toList());
    }
}

