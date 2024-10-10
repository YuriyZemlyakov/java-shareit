package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;

import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestResponseDto addRequest(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                        @RequestBody RequestRequestDto requestDto) {
        return requestService.addRequest(requesterId, requestDto);
    }

    @GetMapping("/all")
    public Collection<RequestResponseDto> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping()
    public Collection<RequestResponseDto> getRequesterRequests(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return requestService.getRequesterRequests(requesterId);
    }
    @GetMapping("/{requestId}")
    public RequestResponseDto getRequestById(@PathVariable long requestId) {
        return requestService.getRequestById(requestId);
    }
}
