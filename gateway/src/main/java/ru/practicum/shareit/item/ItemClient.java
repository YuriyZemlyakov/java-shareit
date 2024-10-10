package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.EditItemRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Component
public class ItemClient extends BaseClient {
    private final static String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> addItem(long userId, ItemDto dto) {
        return post("", 0, dto);
    }

    public ResponseEntity<Object> addComment(long userId, long itemId, CommentRequestDto dto) {
        return post("/" + itemId + "/comment", userId, dto);
    }

    public ResponseEntity<Object> getItemById(long itemId) {
        return get("/" + itemId, itemId);
    }

    public ResponseEntity<Object> getItemsByOwner(long ownerId) {
        return get("/owner", ownerId);
    }

    public ResponseEntity<Object> searchItem(String text) {
        return get("/owner?text=" + text);
    }

    public ResponseEntity<Object> editItem(long itemId, long userId, EditItemRequestDto dto) {
        return patch("/" + itemId, userId, dto);
    }
}
