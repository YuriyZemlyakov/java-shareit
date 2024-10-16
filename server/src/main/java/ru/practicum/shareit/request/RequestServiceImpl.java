package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.dto.ItemReqResDto;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestStorage requestStorage;
    private final RequestMapper requestMapper;
    private final ItemDtoMapper itemMapper;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Override
    public RequestResponseDto addRequest(long requesterId, RequestRequestDto requestDto) {
        User requester = userStorage.getReferenceById(requesterId);
        Request request = requestMapper.dtoToEntity(requestDto);
        request.setRequester(requester);
        return requestMapper.entityToDto(requestStorage.save(request));
    }

    @Override
    public Collection<RequestResponseDto> getRequesterRequests(long requesterId) {
        return requestStorage.findByRequester_idEquals(requesterId).stream()
                .map(request -> requestMapper.entityToDto(request))
                .peek(dto -> dto.setItems(getRequestItems(dto)))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestResponseDto> getAllRequests() {
        return requestStorage.findAll().stream()
                .map(request -> requestMapper.entityToDto(request))
                .collect(Collectors.toList());
    }

    @Override
    public RequestResponseDto getRequestById(long requestId) {
        RequestResponseDto dto = requestMapper.entityToDto(requestStorage.getReferenceById(requestId));
        dto.setItems(getRequestItems(dto));
        return dto;
    }

    private Collection<ItemReqResDto> getRequestItems(RequestResponseDto dto) {
        return itemStorage.findByRequest(dto.getId()).stream()
                .map(item -> itemMapper.itemToItemReqResDto(item))
                .collect(Collectors.toList());


    }
}
