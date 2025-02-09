package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        List<User> users = userRepository.findUserByEmail(userDTO.getEmail().trim());

        if (!users.isEmpty()) {
            throw new ConflictException(String.format("User with email %s already exists", userDTO.getEmail()));
        }

        User saved = userRepository.save(UserMapper.toNewUser(userDTO));
        return UserMapper.toUserDTO(saved);
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));

        if (userDTO.getEmail() != null && !userDTO.getEmail().isBlank()) {
            String email = userDTO.getEmail().trim();
            List<User> users = userRepository.findUserByEmail(email);

            if (!users.isEmpty()
                && !Objects.equals(users.getFirst().getId(), user.getId())
                && Objects.equals(users.getFirst().getEmail(), email)) {
                throw new ConflictException(String.format("User with email %s already exists", email));
            }
        } else {
            userDTO.setEmail(user.getEmail());
        }

        if (userDTO.getName() == null || userDTO.getName().isBlank()) {
            userDTO.setName(user.getName());
        }

        User saved = userRepository.save(UserMapper.toUser(userDTO, id));
        return UserMapper.toUserDTO(saved);
    }

    @Transactional(readOnly = true)
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        return UserMapper.toUserDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s not found", id)));
        userRepository.deleteById(id);
    }
}
