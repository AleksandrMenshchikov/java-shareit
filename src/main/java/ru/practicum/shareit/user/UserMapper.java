package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static User toNewUser(final UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName().trim());
        user.setEmail(userDTO.getEmail().trim());
        return user;
    }

    public static User toUser(final UserDTO userDTO, final Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName(userDTO.getName().trim());
        user.setEmail(userDTO.getEmail().trim());
        return user;
    }

    public static UserDTO toUserDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
