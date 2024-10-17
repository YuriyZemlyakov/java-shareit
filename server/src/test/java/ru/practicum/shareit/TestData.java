package ru.practicum.shareit;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.*;
import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public class TestData {
    public static User user() {
        User dto = new User();
        dto.setId(1L);
        dto.setName("Ivan");
        dto.setEmail("ivan@yandex.ru");
        return dto;
    }

    public static User user2() {
        User dto = new User();
        dto.setId(1L);
        dto.setName("Boris");
        dto.setEmail("boris@yandex.ru");
        return dto;
    }

    public static UserDto owner() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("Ivan");
        dto.setEmail("ivan@yandex.ru");
        return dto;
    }

    public static UserDto booker() {
        UserDto dto = new UserDto();
        dto.setId(10L);
        dto.setName("Boris");
        dto.setEmail("boris@yandex.ru");
        return dto;
    }

    public static EditedUserFields editedUserFields() {
        EditedUserFields dto = new EditedUserFields();
        dto.setName("Yen");
        return dto;
    }

    public static Item item() {
        Item dto = new Item();
        dto.setId(2L);
        dto.setName("hummer");
        dto.setDescription("small black hummer");
        dto.setAvailable(true);
        dto.setOwner(user());
        return dto;
    }


    public static ItemDto itemDto() {
        ItemDto dto = new ItemDto();
        dto.setId(2L);
        dto.setName("hummer");
        dto.setDescription("small black hummer");
        dto.setAvailable(true);
        dto.setOwner(owner());
        return dto;
    }

    public static ItemResponseDto itemResponseDto() {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(2L);
        dto.setName("hummer");
        dto.setDescription("small black hummer");
        dto.setAvailable(true);
        dto.setOwner(user());
        dto.setRequestId(1L);
        return dto;
    }

    public static OwnerItemDto ownerItemDto() {
        OwnerItemDto dto = new OwnerItemDto();
        dto.setId(2L);
        dto.setName("hummer");
        dto.setDescription("small black hummer");
        dto.setAvailable(true);
        dto.setOwner(user());
        dto.setComments(List.of(commentResponseDto()));
        dto.setRequestId(1L);
        dto.setComments(List.of(commentResponseDto()));
        dto.setLastBooking(LocalDateTime.of(2024, 10, 11, 22, 31, 55));
        dto.setNextBooking(LocalDateTime.of(2024, 10, 12, 22, 31, 55));
        return dto;
    }

    public static ItemReqResDto itemReqResDto() {
        ItemReqResDto dto = new ItemReqResDto();
        dto.setId(2L);
        dto.setName("rare thing");
        dto.setOwnerId(1L);

        return dto;
    }

    public static EditItemRequestDto editItemRequestDto() {
        EditItemRequestDto dto = new EditItemRequestDto();
        dto.setDescription("small black hummer");
        dto.setAvailable(true);
        return dto;
    }

    public static CommentRequestDto commentRequestDto() {
        CommentRequestDto dto = new CommentRequestDto();
        dto.setText("nice thing");
        return dto;
    }

    public static CommentResponseDto commentResponseDto() {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(1L);
        dto.setText("nice thing");
        dto.setItem(item());
        dto.setAuthorName("Boris");
        return dto;
    }

    public static Booking booking() {
        Booking dto = new Booking();
        dto.setId(1L);
        dto.setItem(item());
        dto.setStart(LocalDateTime.of(2024, 10, 16, 22, 31, 55));
        dto.setEnd(LocalDateTime.of(2024, 10, 18, 22, 31, 55));
        dto.setBooker(user2());
        dto.setStatus(Status.WAITING);
        return dto;
    }

    public static BookingRequestDto bookingRequestDto() {
        BookingRequestDto dto = new BookingRequestDto();
        dto.setItemId(2L);
        dto.setStart(LocalDateTime.of(2024, 10, 16, 22, 31, 55));
        dto.setEnd(LocalDateTime.of(2024, 10, 18, 22, 31, 55));
        return dto;
    }

    public static BookingResponseDto bookingResponseDto() {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(1L);
        dto.setItem(itemResponseDto());
        dto.setStart(LocalDateTime.of(2024, 10, 16, 22, 31, 55));
        dto.setEnd(LocalDateTime.of(2024, 10, 18, 22, 31, 55));
        dto.setBooker(booker());
        dto.setStatus(Status.WAITING);
        return dto;
    }

    public static RequestRequestDto requestRequestDto() {
        RequestRequestDto dto = new RequestRequestDto();
        dto.setDescription("rare thing");
        return dto;
    }

    public static RequestResponseDto requestResponseDto() {
        RequestResponseDto dto = new RequestResponseDto();
        dto.setId(1L);
        dto.setDescription("rare thing");
        dto.setRequesterId(2L);
        dto.setItems(List.of(itemReqResDto()));
        return dto;
    }
}
