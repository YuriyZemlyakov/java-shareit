package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingStorage;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.dtoMapper.BookingDtoMapper;
import ru.practicum.shareit.booking.model.enums.State;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;
    private final BookingDtoMapper mapper;

    @Override
    public Booking addBooking(long userId, BookingRequestDto newBooking) {
        User booker = checkUser(userId);
        long itemId = newBooking.getItemId();
        Item item = checkItem(itemId);
        boolean isAvailable = item.getAvailable();
        if (!isAvailable) {
            throw new ValidationException(String.format("Вещь %s недоступна для бронирования", newBooking.getItemId()));
        }
        Booking requestEntityBooking = mapper.requestDtoToEntity(newBooking);
        requestEntityBooking.setStatus(Status.WAITING);
        requestEntityBooking.setBooker(booker);
        requestEntityBooking.setItem(item);
        Booking responseEntity = bookingStorage.save(requestEntityBooking);
        return responseEntity;
    }

    @Override
    public BookingResponseDto confirmBooking(long userId, long bookingId, boolean isApproved) {

        if (userId != bookingStorage.getOwnerIdByBookingId(bookingId)) {
            throw new AccessException("Акцептовать бронирование может только владелец вещи");
        }
        Booking editedBooking = bookingStorage.getReferenceById(bookingId);
        Status status = null;
        if (isApproved) {
            status = Status.APPROVED;
        } else {
            status = Status.REJECTED;
        }
        editedBooking.setStatus(status);
        return mapper.entityToResponseDto(bookingStorage.save(editedBooking));
    }

    @Override
    public BookingResponseDto getBookingInfo(long bookingId) {
        return mapper.entityToResponseDto(bookingStorage.getReferenceById(bookingId));
    }

    @Override
    public Collection<BookingResponseDto> getAlLBookingsByCurrentUser(long userId, State state) {
        checkUser(userId);
        switch (state) {
            case State.ALL:
                return bookingStorage.getAllBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.CURRENT:
                return bookingStorage.getCurrentBookings(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.FUTURE:
                return bookingStorage.getAllFutureBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.PAST:
                return bookingStorage.getAllPastBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.WAITING:
                return bookingStorage.getAllWaitingBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.REJECTED:
                return bookingStorage.getAllRejectedBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            default:
                return bookingStorage.getAllBookingsByCurrentUser(userId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());

        }
    }

    @Override
    public Collection<BookingResponseDto> getBookingsByOwner(long ownerId, State state) {
        checkUser(ownerId);
        switch (state) {
            case State.ALL:
                return bookingStorage.getOwnerBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.CURRENT:
                return bookingStorage.getOwnerCurrentBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.FUTURE:
                return bookingStorage.getOwnerFutureBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.PAST:
                return bookingStorage.getOwnerPastBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.WAITING:
                return bookingStorage.getOwnerWaitingBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            case State.REJECTED:
                return bookingStorage.getOwnerRejectedBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());
            default:
                return bookingStorage.getOwnerBookings(ownerId).stream()
                        .map(booking -> mapper.entityToResponseDto(booking))
                        .collect(Collectors.toList());

        }
    }

    private User checkUser(long userId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("Пользователя с id %s не найдено", userId));
                });
        return user;
    }

    private Item checkItem(long itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> {
            throw new NotFoundException("вещь не найдена");
        });
        return item;
    }
}
