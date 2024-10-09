package ru.practicum.shareit.booking.model.dtoMapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.user.model.dtoMappers.UserDtoMapper;

@Mapper(componentModel = "spring", uses = {ItemDtoMapper.class, UserDtoMapper.class})
public interface BookingDtoMapper {
    BookingResponseDto entityToResponseDto(Booking booking);

    Booking requestDtoToEntity(BookingRequestDto bookingRequestDto);
}
