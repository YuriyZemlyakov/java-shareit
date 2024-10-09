package ru.practicum.shareit.item.model.dto;

import lombok.Data;

@Data
public class EditItemRequestDto {
    private String name;
    private String description;
    private Boolean available;
}
