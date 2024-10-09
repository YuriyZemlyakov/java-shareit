package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.dto.ItemReqResDto;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class RequestResponseDto {
    private long id;
    private long requesterId;
    private String description;
    private LocalDateTime created;
    private Collection<ItemReqResDto> items;
}
