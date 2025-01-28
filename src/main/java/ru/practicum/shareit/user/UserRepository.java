package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.shared.BaseRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email) {
        return getData().values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
}
