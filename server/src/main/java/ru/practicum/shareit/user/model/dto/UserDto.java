package ru.practicum.shareit.user.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class UserDto {
    private long id;
    private String name;
    @UniqueElements
    private String email;
}
