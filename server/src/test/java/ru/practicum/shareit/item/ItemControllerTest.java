package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.item.model.dto.EditItemRequestDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private ItemController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Add Item Test")
    void testAddItem() throws Exception {
        // Given
        ItemDto itemDto = TestData.itemDto();
        Mockito.when(itemService.addItem(1, itemDto)).thenReturn(itemDto);

        // When
        var response = mockMvc.perform(
                        post("/items")
                                .header("X-Sharer-User-Id", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(itemDto))
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        verify(itemService).addItem(1, itemDto);
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(itemDto));
    }

    @Test
    @DisplayName("Add Comment Test")
    void testAddComment() throws Exception {
        // Given
        var itemId = 2L;
        var userId = 1;
        var requestDto = TestData.commentRequestDto();
        var responseDto = TestData.commentResponseDto();
        Mockito.when(itemService.addComment(userId, itemId, requestDto)).thenReturn(responseDto);

        // When
        var response = mockMvc.perform(
                        post("/items/{itemId}/comment", itemId)
                                .header("X-Sharer-User-Id", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("nice thing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("Boris"));

        // Then
        verify(itemService).addComment(userId, itemId, requestDto);
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(TestData.commentResponseDto()));
    }

    @Test
    @DisplayName("Get Item by ID Test")
    void testGetItemById() throws Exception {
        // Given
        var itemId = 2L;
        var expectedItem = TestData.ownerItemDto();
        Mockito.when(itemService.getItem(itemId)).thenReturn(expectedItem);

        // When
        var response = mockMvc.perform(
                        get("/items/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastBooking").value("2024-10-11T22:31:55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextBooking").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments").exists());


        // Then
        verify(itemService).getItem(eq(itemId));
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expectedItem));
    }

    @Test
    @DisplayName("Get Items By Owner Test")
    void testGetItemsByOwner() throws Exception {
        // Given
        var ownerId = 1L;
        var items = Arrays.asList(TestData.ownerItemDto());
        Mockito.when(itemService.getItemsByOwner(ownerId)).thenReturn(items);

        // When
        var response = mockMvc.perform(
                get("/items")
                .header("X-Sharer-User-Id", String.valueOf(ownerId))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(itemService).getItemsByOwner(ownerId);
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(items));
    }

    @Test
    @DisplayName("Search Item Test")
    void testSearchItem() throws Exception {
        // Given
        var text = "some query";
        var items = Arrays.asList(new ItemDto(), new ItemDto());
        given(itemService.searchItem(any())).willReturn(items);

        // When
        var response = mockMvc.perform(
                get("/items/search?text=" + text)
        ).andExpect(status().isOk());

        // Then
        verify(itemService).searchItem(eq(text));
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(items));
    }

    @Test
    @DisplayName("Edit Item Test")
    void testEditItem() throws Exception {
        // Given
        var itemId = 1L;
        var userId = 2;
        var editedItemRequestDto = TestData.editItemRequestDto();
        Mockito.when(itemService.editItem(itemId, userId, editedItemRequestDto)).thenReturn(TestData.itemDto());

        // When
        var response = mockMvc.perform(
                patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedItemRequestDto)))
                        .andExpect(status().isOk());

        // Then
        verify(itemService).editItem(itemId, userId, editedItemRequestDto);
        assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(TestData.itemDto()));
    }
}