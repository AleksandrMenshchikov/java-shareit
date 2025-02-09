package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public final class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO createUser(@Validated(UserDTO.Create.class) @RequestBody final UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PatchMapping("/{userId}")
    UserDTO updateUser(@Validated(UserDTO.Update.class) @RequestBody final UserDTO userDTO,
                               @PathVariable final Long userId) {
        return userService.updateUser(userDTO, userId);
    }

    @GetMapping("/{userId}")
    UserDTO getUser(@PathVariable final Long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
    }
}
