package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.enums.State;

import java.util.Collection;

public interface BookingService {
    Booking addBooking(long bookerId, BookingRequestDto newBooking);
    BookingResponseDto confirmBooking(long bookingId, boolean isApproved);
    BookingResponseDto getBookingInfo(long bookingId);
    Collection<BookingResponseDto> getAlLBookingsByCurrentUser(long userId, State state);
    Collection<BookingResponseDto> getBookingsByOwner(long ownerId, State state);

}
