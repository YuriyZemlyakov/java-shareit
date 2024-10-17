package ru.practicum.shareit.item.model.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.dto.UserDto;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private Long requestId;
}
