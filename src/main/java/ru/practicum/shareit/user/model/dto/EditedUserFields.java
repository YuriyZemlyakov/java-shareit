package ru.practicum.shareit.user.model.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EditedUserFields {
    private String name;
    @Email
    private String email;
}
