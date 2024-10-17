package ru.practicum.shareit.itemRequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.model.dto.ItemReqResDto;
import ru.practicum.shareit.item.model.dtoMapper.ItemDtoMapper;
import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestStorage itemRequestStorage;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemDtoMapper itemMapper;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Override
    public RequestResponseDto addRequest(long requesterId, RequestRequestDto requestDto) {
        User requester = userStorage.getReferenceById(requesterId);
        ItemRequest itemRequest = itemRequestMapper.dtoToEntity(requestDto);
        itemRequest.setRequester(requester);
        return itemRequestMapper.entityToDto(itemRequestStorage.save(itemRequest));
    }

    @Override
    public Collection<RequestResponseDto> getRequesterRequests(long requesterId) {
        return itemRequestStorage.findByRequester_idEquals(requesterId).stream()
                .map(itemRequest -> itemRequestMapper.entityToDto(itemRequest))
                .peek(dto -> dto.setItems(getRequestItems(dto)))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestResponseDto> getAllRequests() {
        return itemRequestStorage.findAll().stream()
                .map(itemRequest -> itemRequestMapper.entityToDto(itemRequest))
                .collect(Collectors.toList());
    }

    @Override
    public RequestResponseDto getRequestById(long requestId) {
        RequestResponseDto dto = itemRequestMapper.entityToDto(itemRequestStorage.getReferenceById(requestId));
        dto.setItems(getRequestItems(dto));
        return dto;
    }

    private Collection<ItemReqResDto> getRequestItems(RequestResponseDto dto) {
        return itemStorage.findByRequest(dto.getId()).stream()
                .map(item -> itemMapper.itemToItemReqResDto(item))
                .collect(Collectors.toList());


    }
}
