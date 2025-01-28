package ru.practicum.shareit.shared;

import lombok.Getter;

import java.util.HashMap;
import java.util.Optional;

public abstract class BaseRepository<T> {
    private Long id = 0L;
    @Getter
    private final HashMap<Long, T> data = new HashMap<>();

    public Optional<T> findById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    public void insert(T entity, Long id) {
        data.put(id, entity);
    }

    public void updateById(long id, T entity) {
        data.replace(id, entity);
    }

    public void deleteById(long id) {
        data.remove(id);
    }

    public Long generateId() {
        id += 1;
        return id;
    }
}
