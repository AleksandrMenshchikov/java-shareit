package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.CreateUserDTO;
import ru.practicum.shareit.user.dto.ResponseUserDTO;
import ru.practicum.shareit.user.dto.UpdateUserDTO;
import ru.practicum.shareit.user.model.User;

public final class UserMapper {
    public static User toUser(final CreateUserDTO createUserDTO, Long userId) {
        return User.builder()
                .id(userId)
                .name(createUserDTO.getName())
                .email(createUserDTO.getEmail())
                .build();
    }

    public static User toUser(final UpdateUserDTO updateUserDTO, Long userId) {
        return User.builder()
                .id(userId)
                .email(updateUserDTO.getEmail())
                .name(updateUserDTO.getName())
                .build();
    }

    public static ResponseUserDTO toResponseUserDTO(final User user) {
        return ResponseUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
