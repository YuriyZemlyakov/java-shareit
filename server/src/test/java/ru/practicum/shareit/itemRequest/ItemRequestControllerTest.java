package ru.practicum.shareit.itemRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.itemRequest.dto.RequestRequestDto;
import ru.practicum.shareit.itemRequest.dto.RequestResponseDto;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
public class ItemRequestControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ItemRequestService itemRequestService;
    @InjectMocks
    private ItemRequestController itemRequestController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemRequestController)
                .build();
    }

    @Test
    public void addRequestTest() throws Exception {
        RequestRequestDto requestDto = TestData.requestRequestDto();
        RequestResponseDto responseDto = TestData.requestResponseDto();
        Long userId = 2L;

        Mockito.when(itemRequestService.addRequest(Mockito.anyLong(), Mockito.any(RequestRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("rare thing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.requesterId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void getAllRequestsTest() throws Exception {
        Collection<RequestResponseDto> expectedDtos = List.of(TestData.requestResponseDto());

        Mockito.when(itemRequestService.getAllRequests()).thenReturn(expectedDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("rare thing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].requesterId").exists());

    }

    @Test
    public void getRequestByIdTest() throws Exception {
        RequestResponseDto expectedDto = TestData.requestResponseDto();

        Mockito.when(itemRequestService.getRequestById(Mockito.anyLong())).thenReturn(expectedDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/{requestId}", 2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("rare thing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.requesterId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void getRequesterRequestsTest() throws Exception {
        Collection<RequestResponseDto> expectedDtos = List.of(TestData.requestResponseDto());
        Long requesterId = 1L;

        Mockito.when(itemRequestService.getRequesterRequests(Mockito.anyLong())).thenReturn(expectedDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests")
                        .header("X-Sharer-User-Id", requesterId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("rare thing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].requesterId").exists());

    }

}
