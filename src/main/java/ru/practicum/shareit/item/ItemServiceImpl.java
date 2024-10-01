package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingStorage;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dao.CommentStorage;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.dtoMapper.CommentMapper;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.user.dao.UserStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.max;
import static java.util.Collections.reverseOrder;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;
    private final CommentMapper commentMapper;

    @Override
    public ItemDto getItem(long itemId) {
        return ItemDtoMapper.itemToDto(itemStorage.getReferenceById(itemId));
    }


    @Override
    public Collection<OwnerItemDto> getItemsByOwner(long ownerId) {
        return itemStorage.getItemsByOwner(ownerId).stream()
                .map(item -> ItemDtoMapper.itemToOwnerDto(item))
                .peek(ownerItemDto -> {
                    long itemId = ownerItemDto.getId();
                    Collection<Booking> bookings = bookingStorage.getBookingsByItem(itemId);
                    ownerItemDto.setPreviousBookingDate(getLastBookingDate(bookings).orElse(null));
                    ownerItemDto.setNextBookingDate(getNextBookingDate(bookings).orElse(null));
                    Collection<CommentResponseDto> comments = commentStorage
                            .findAllByAuthorAndItem(ownerId, itemId)
                            .stream()
                            .map(comment -> commentMapper.entityToResponsetDto(comment))
                            .collect(Collectors.toList());
                    ownerItemDto.setComment(comments);
                })
                .collect(Collectors.toList());

    }
    private Optional<LocalDateTime> getLastBookingDate(Collection<Booking> bookingList) {
        return bookingList.stream()
                .filter(booking -> (booking.getEnd().isBefore(LocalDateTime.now())))
                .map(booking -> booking.getEnd())
                .sorted(reverseOrder())
                .findFirst();
    }
    private Optional<LocalDateTime> getNextBookingDate(Collection<Booking> bookingList) {
        return bookingList.stream()
                .filter(booking -> (booking.getStart().isAfter(LocalDateTime.now())))
                .map(booking -> booking.getStart())
                .sorted()
                .findFirst();
    }


    @Override
    public ItemDto addItem(long userId, ItemDto newItem) {
        if (userStorage.getReferenceById(userId) == null) {
            throw new NotFoundException(String.format("Пользователь с id {} не найден", userId));
        }
        Item item = ItemDtoMapper.dtoToItem(newItem);
        item.setOwner(userStorage.getReferenceById(userId));
        return ItemDtoMapper.itemToDto(itemStorage.save(item));
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
        return ItemDtoMapper.itemToDto(itemStorage.save(editedItem));
    }

    @Override
    public Collection<ItemDto> searchItem(String text) {
        return itemStorage.searchItem(text.toLowerCase()).stream()
                .map(item -> ItemDtoMapper.itemToDto(item))
                .collect(Collectors.toList());
    }
    @Override
    public CommentResponseDto addComment(long userId, long itemId, CommentRequestDto requestDto) {
        checkCommentAuthor(userId, itemId);
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
    private void checkCommentAuthor(long userId, long itemId) {
        if (!itemStorage.getItemsByOwner(userId).contains(itemStorage.getReferenceById(itemId))) {
            throw new ValidationException(String.format("Пользователь %s не брал в аренду вещь %s, поэтому не имеет" +
                    "право писать комментарий", userId, itemId));
        }
    }
}
