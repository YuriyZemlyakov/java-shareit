package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.enums.State;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService service;

    @PostMapping
    public Booking addBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody BookingRequestDto newBooking) {
        log.info("Received POST request: {}", newBooking);
        return service.addBooking(userId, newBooking);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto confirmBooking(@PathVariable long bookingId,
                                 @RequestParam(name = "approved") boolean isApproved) {
        return service.confirmBooking(bookingId, isApproved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBookingInfo(@PathVariable long bookingId) {
        return service.getBookingInfo(bookingId);
    }
    @GetMapping
    public Collection<BookingResponseDto> getAllBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                         @RequestParam(required = false, defaultValue = "ALL") State state) {
        return service.getAlLBookingsByCurrentUser(userId, state);
    }
    @GetMapping("/owner")
    public Collection<BookingResponseDto> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                                             @RequestParam(required = false, defaultValue = "ALL") State state) {
        return service.getBookingsByOwner(ownerId, state);
    }
}
