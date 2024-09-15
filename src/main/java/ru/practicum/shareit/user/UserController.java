package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.dto.EditedUserFields;
import ru.practicum.shareit.user.model.dto.UserDto;

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
        validateUser(user);
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody EditedUserFields editedUserFields) {
        validtateEditUserDto(editedUserFields);
        return userService.updateUser(userId, editedUserFields);
    }

    private void validateUser(UserDto userDto) {
        if (userDto.getName().isBlank()) {
            throw new ValidationException("Имя пользователя должно быть заполнено");
        }
        if (userDto.getEmail().isBlank()) {
            throw new ValidationException("email должен быть заполнен");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат email");
        }
    }

    private void validtateEditUserDto(EditedUserFields editedUserFields) {
        if (editedUserFields.getName() != null && editedUserFields.getName().isBlank()) {
            throw new ValidationException("Имя пользователя должно быть заполнено");
        }
        if (editedUserFields.getEmail() != null && editedUserFields.getEmail().isBlank()) {
            throw new ValidationException("email должен быть заполнен");
        }
        if (editedUserFields.getEmail() != null && !editedUserFields.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат email");
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
