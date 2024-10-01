package ru.practicum.shareit.item.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class OwnerItemDto {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private User owner;
    private long request = 0;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private Collection<CommentResponseDto> comments;
}
