package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.shared.exception.ConflictException;
import ru.practicum.shareit.shared.exception.InternalServerErrorException;
import ru.practicum.shareit.shared.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent()) {
            throw new ConflictException(String.format("User with email %s already exists", userDTO.getEmail()));
        }

        Long id = userRepository.generateId();
        userDTO.setId(id);
        User user = UserMapper.toUser(userDTO);
        userRepository.insert(user, id);
        return UserMapper.toUserDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User oldUser = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));

        if (userDTO.getEmail() == null || oldUser.getEmail().equals(userDTO.getEmail())) {
            userDTO.setEmail(oldUser.getEmail());
        } else {
            userRepository.findByEmail(userDTO.getEmail()).ifPresent((user) -> {
                if (user.getEmail().equals(userDTO.getEmail())) {
                    throw new ConflictException(String.format("User with email %s already exists", userDTO.getEmail()));
                }
            });
        }

        if (userDTO.getName() == null || userDTO.getName().isBlank()) {
            userDTO.setName(oldUser.getName());
        }

        userDTO.setId(id);
        User user = UserMapper.toUser(userDTO);
        userRepository.updateById(id, user);
        User newUser = userRepository.findById(id).orElseThrow(() ->
                new InternalServerErrorException(String.format("User with id %s not found", id)));
        return UserMapper.toUserDTO(newUser);
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        return UserMapper.toUserDTO(user);
    }


    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        userRepository.deleteById(id);
    }
}
