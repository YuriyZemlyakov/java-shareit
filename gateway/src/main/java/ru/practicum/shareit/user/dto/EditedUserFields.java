package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EditedUserFields {
    private String name;
    @Email
    private String email;
}
