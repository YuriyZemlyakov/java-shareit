package ru.practicum.shareit.item.model;

import lombok.Data;

@Data
public class EditItemRequestDto {
    private String name;
    private String description;
    private boolean available;
}
