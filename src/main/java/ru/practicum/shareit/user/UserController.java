package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        return userService.getUser(userId);
    }
    @PostMapping
    public UserDto addUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }
    @PutMapping
    public UserDto updateUser(@RequestBody UserDto editedUser) {
        return userService.updateUser(editedUser);
    }
}
