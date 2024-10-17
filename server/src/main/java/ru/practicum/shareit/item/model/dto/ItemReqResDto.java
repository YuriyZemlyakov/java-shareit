package ru.practicum.shareit.item.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemReqResDto {
    private long id;
    private String name;
    private long ownerId;
}
