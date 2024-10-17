package ru.practicum.shareit.itemRequest;

import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;

import java.util.Collection;

public interface ItemRequestService {
    RequestResponseDto addRequest(long requesterId, RequestRequestDto requestDto);

    Collection<RequestResponseDto> getRequesterRequests(long requesterId);

    Collection<RequestResponseDto> getAllRequests();

    RequestResponseDto getRequestById(long requestId);


}
