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
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.user.dao.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    final private BookingStorage bookingStorage;
    final private UserStorage userStorage;
    final private ItemStorage itemStorage;
    final private BookingDtoMapper mapper;
    @Override
    public Booking addBooking(long userId, BookingRequestDto newBooking) {
        log.info("Маппим дто в энтити");
        Booking requestEntityBooking = mapper.requestDtoToEntity(newBooking);
        log.info("Проставляем статус");
        requestEntityBooking.setStatus(Status.WAITING);
        log.info("проставляем юзера");
        requestEntityBooking.setBooker(userStorage.getReferenceById(userId));
        log.info("проставляем Item");
        requestEntityBooking.setItem(itemStorage.getReferenceById(newBooking.getItemId()));
        log.info("сохраняем данные в БД");
        Booking responseEntity = bookingStorage.save(requestEntityBooking);
        log.info("преобразуем сущность в энтити и возвращаем в контрооллер");
        return responseEntity;
    }

    @Override
    public BookingResponseDto confirmBooking(long bookingId, boolean isApproved) {
        Booking editedBooking = bookingStorage.getReferenceById(bookingId);
        Status status = null;
        if (isApproved) {
            status = Status.APPROVED;
            editedBooking.setStatus(status);
            return mapper.entityToResponseDto(bookingStorage.save(editedBooking));
        } else {
            status = Status.REJECTED;
            editedBooking.setStatus(status);
            return mapper.entityToResponseDto(bookingStorage.save(editedBooking));
        }
    }

    @Override
    public BookingResponseDto getBookingInfo(long bookingId) {
        return mapper.entityToResponseDto(bookingStorage.getReferenceById(bookingId));
    }

    @Override
    public Collection<BookingResponseDto> getAlLBookingsByCurrentUser(long userId, State state) {
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
}
