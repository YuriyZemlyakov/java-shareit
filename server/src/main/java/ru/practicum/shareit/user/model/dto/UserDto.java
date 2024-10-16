package ru.practicum.shareit.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class UserDto {
    private long id;
    private String name;
    @NotBlank
    @Email
    @UniqueElements
    private String email;
}
