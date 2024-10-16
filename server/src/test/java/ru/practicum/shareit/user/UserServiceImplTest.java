package ru.practicum.shareit.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dtoMappers.UserDtoMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStorage userStorage;
    private User testUser;
    private long userId = 0;
    @Autowired
    private UserDtoMapper userDtoMapper;


    @BeforeEach
    void setUp() {
        testUser = TestData.user();
        userId = userStorage.save(testUser).getId();
    }

    @AfterEach
    void tearDown() {
        userStorage.deleteById(userId);
        userStorage.deleteById(userId + 1);
    }

    @Test
    void testGetAllUsers() {
        assertThat(userService.getAllUsers()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void testGetUserByExistingId() {
        User savedUser = userDtoMapper.dtoToUser(userService.getUser(userId));
        Assertions.assertNotNull(savedUser);
        assertThat(savedUser.getName()).isEqualTo("Ivan");
        assertThat(savedUser.getEmail()).isEqualTo("ivan@yandex.ru");
    }

    @Test
    void testGetUserByNonExistingId() {
        assertThrows(NotFoundException.class, () -> userService.getUser(userId + 1));
    }

    @Test
    void testAddNewUser() {
        User newUser = TestData.user2();
        User addedUser = userDtoMapper.dtoToUser(userService.addUser(userDtoMapper.userToDto(newUser)));
        assertThat(addedUser.getName()).isEqualTo("Boris");
        assertThat(addedUser.getEmail()).isEqualTo("boris@yandex.ru");
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(userId);
        assertThrows(NotFoundException.class, () -> userService.getUser(userId));
    }
}
