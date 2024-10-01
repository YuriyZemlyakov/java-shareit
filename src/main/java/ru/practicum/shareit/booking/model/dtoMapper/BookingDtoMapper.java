package ru.practicum.shareit.booking.model.dtoMapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;

@Mapper(componentModel = "spring")
public interface BookingDtoMapper {
    BookingResponseDto entityToResponseDto(Booking booking);

    Booking requestDtoToEntity(BookingRequestDto bookingRequestDto);
}
