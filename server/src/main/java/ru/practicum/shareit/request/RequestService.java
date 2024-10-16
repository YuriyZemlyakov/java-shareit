package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;

import java.util.Collection;

public interface RequestService {
    RequestResponseDto addRequest(long requesterId, RequestRequestDto requestDto);

    Collection<RequestResponseDto> getRequesterRequests(long requesterId);

    Collection<RequestResponseDto> getAllRequests();

    RequestResponseDto getRequestById(long requestId);


}
