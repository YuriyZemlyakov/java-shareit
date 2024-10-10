package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                             @RequestBody RequestRequestDto requestDto) {
        return requestClient.addRequest(requesterId, requestDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests() {
        return requestClient.getAllRequests();
    }

    @GetMapping()
    public ResponseEntity<Object> getRequesterRequests(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return requestClient.getRequesterRequests(requesterId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable long requestId) {
        return requestClient.getRequestById(requestId);
    }
}
