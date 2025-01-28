package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.shared.exception.ConflictException;
import ru.practicum.shareit.shared.exception.InternalServerErrorException;
import ru.practicum.shareit.shared.exception.NotFoundException;
import ru.practicum.shareit.user.dto.CreateUserDTO;
import ru.practicum.shareit.user.dto.ResponseUserDTO;
import ru.practicum.shareit.user.dto.UpdateUserDTO;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository userRepository;

    public ResponseUserDTO createUser(CreateUserDTO createUserDTO) {
        Optional<User> userOptional = userRepository.findByEmail(createUserDTO.getEmail());

        if (userOptional.isPresent()) {
            throw new ConflictException(String.format("User with email %s already exists", createUserDTO.getEmail()));
        }

        Long id = userRepository.generateId();
        User user = UserMapper.toUser(createUserDTO, id);
        userRepository.insert(user, id);
        return UserMapper.toResponseUserDTO(user);
    }

    public ResponseUserDTO updateUser(UpdateUserDTO updateUserDTO, Long id) {
        User oldUser = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));

        if (updateUserDTO.getEmail() == null || oldUser.getEmail().equals(updateUserDTO.getEmail())) {
            updateUserDTO.setEmail(oldUser.getEmail());
        } else {
            userRepository.findByEmail(updateUserDTO.getEmail()).ifPresent((user) -> {
                if (user.getEmail().equals(updateUserDTO.getEmail())) {
                    throw new ConflictException(String.format("User with email %s already exists", updateUserDTO.getEmail()));
                }
            });
        }

        if (updateUserDTO.getName() == null || updateUserDTO.getName().isBlank()) {
            updateUserDTO.setName(oldUser.getName());
        }

        User user = UserMapper.toUser(updateUserDTO, id);
        userRepository.updateById(id, user);
        User newUser = userRepository.findById(id).orElseThrow(() ->
                new InternalServerErrorException(String.format("User with id %s not found", id)));
        return UserMapper.toResponseUserDTO(newUser);
    }

    public ResponseUserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        return UserMapper.toResponseUserDTO(user);
    }


    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        userRepository.deleteById(id);
    }
}
