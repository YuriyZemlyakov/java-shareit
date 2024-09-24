package ru.practicum.shareit.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;


}
