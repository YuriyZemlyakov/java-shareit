package ru.practicum.shareit.booking;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.enums.State;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    private MockMvc mockMvc;
    @Mock
    private BookingService bookingService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Successfully adds new booking")
    void successfulAddBooking() throws Exception {
        // Given
        long userId = 1L;
        BookingRequestDto bookingRequestDto = TestData.bookingRequestDto();
        Booking booking = TestData.booking();

        // When
        Mockito.when(bookingService.addBooking(userId, bookingRequestDto)).thenReturn(booking);

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequestDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(booking.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start").value("2024-10-16T22:31:55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.end").value("2024-10-18T22:31:55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("WAITING"));
    }

    @Test
    @DisplayName("Confirm booking with approved state")
    void confirmBookingApproved() throws Exception {
        // Given
        long userId = 1L;
        long bookingId = 2L;
        boolean isApproved = true;
        BookingResponseDto expectedResponseDto = TestData.bookingResponseDto();

        // When
        Mockito.when(bookingService.confirmBooking(userId, bookingId, isApproved)).thenReturn(expectedResponseDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/bookings/{bookingId}", bookingId)
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .param("approved", Boolean.toString(isApproved))
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(expectedResponseDto.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.end").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("WAITING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.booker").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.item").exists());
    }

    @Test
    @DisplayName("Get all bookings by current user")
    void getAllBookingsByCurrentUser() throws Exception {
        // Given
        long userId = 1L;

        Collection<BookingResponseDto> expectedBookingResponseDtos = List.of(TestData.bookingResponseDto());

        // When
        Mockito.when(bookingService.getAlLBookingsByCurrentUser(userId, State.ALL)).thenReturn(expectedBookingResponseDtos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/bookings")
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].start").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].end").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("WAITING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].booker").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].item").exists());
    }

    @Test
    @DisplayName("Get bookings by owner")
    void getBookingsByOwner() throws Exception {
        // Given
        long ownerId = 1L;
        Collection<BookingResponseDto> expectedBookingResponseDtos = List.of(TestData.bookingResponseDto());

        Mockito.when(bookingService.getBookingsByOwner(ownerId, State.ALL)).thenReturn(expectedBookingResponseDtos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/bookings/owner")
                .header("X-Sharer-User-Id", String.valueOf(ownerId))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].start").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].end").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("WAITING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].booker").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].item").exists());

        //

    }
}
