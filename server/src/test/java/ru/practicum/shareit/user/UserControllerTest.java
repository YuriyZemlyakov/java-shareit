package ru.practicum.shareit.user;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Set up expectations for the service method
        Collection<UserDto> userDtos = List.of(TestData.owner(), TestData.booker());
        Mockito.when(userService.getAllUsers()).thenReturn(userDtos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ivan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", is("ivan@yandex.ru")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Boris")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", is("boris@yandex.ru")));

    }

    @Test
    public void testGetUserById() throws Exception {
        // Set up expectation for the service method
        UserDto expectedUser = TestData.owner();
        Mockito.when(userService.getUser(1L)).thenReturn(expectedUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Ivan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("ivan@yandex.ru")));
    }

    @Test
    public void testAddUser() throws Exception {
        // Set up expectation for the service method
        UserDto expectedUser = TestData.owner();
        Mockito.when(userService.addUser(Mockito.any(UserDto.class))).thenReturn(expectedUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedUser))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Ivan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("ivan@yandex.ru")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Set up expectation for the service method
        UserDto expectedUser = TestData.owner();
        EditedUserFields editedUserFields = TestData.editedUserFields();
        Mockito.when(userService.updateUser(1L, editedUserFields)).thenReturn(expectedUser);

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedUserFields))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Ivan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("ivan@yandex.ru")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // No need to set up an expectation here since there are no return values
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
