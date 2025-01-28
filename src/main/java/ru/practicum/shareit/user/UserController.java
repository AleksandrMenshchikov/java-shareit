package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserDTO;
import ru.practicum.shareit.user.dto.ResponseUserDTO;
import ru.practicum.shareit.user.dto.UpdateUserDTO;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public final class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseUserDTO createUser(@Valid @RequestBody final CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @PatchMapping("/{userId}")
    ResponseUserDTO updateUser(@Valid @RequestBody final UpdateUserDTO updateUserDTO,
                               @PathVariable final Long userId) {
        return userService.updateUser(updateUserDTO, userId);
    }

    @GetMapping("/{userId}")
    ResponseUserDTO getUser(@PathVariable final Long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
    }
}
