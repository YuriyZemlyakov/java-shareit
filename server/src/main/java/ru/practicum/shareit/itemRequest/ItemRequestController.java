package ru.practicum.shareit.itemRequest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;

import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public RequestResponseDto addRequest(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                         @RequestBody RequestRequestDto requestDto) {
        return itemRequestService.addRequest(requesterId, requestDto);
    }

    @GetMapping("/all")
    public Collection<RequestResponseDto> getAllRequests() {
        return itemRequestService.getAllRequests();
    }

    @GetMapping()
    public Collection<RequestResponseDto> getRequesterRequests(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return itemRequestService.getRequesterRequests(requesterId);
    }

    @GetMapping("/{requestId}")
    public RequestResponseDto getRequestById(@PathVariable long requestId) {
        return itemRequestService.getRequestById(requestId);
    }
}
