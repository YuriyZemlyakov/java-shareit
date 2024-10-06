package ru.practicum.shareit.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.dto.ItemResponseDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingResponseDto {
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;
    private ItemResponseDto item;
    private UserDto booker;
    private Status status;
}
