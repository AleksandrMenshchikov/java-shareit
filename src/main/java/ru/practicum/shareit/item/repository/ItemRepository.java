package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findItemsByOwner_Id(Long ownerId);

    @Query("select i from Item i where upper(i.name) like upper(concat('%', :name, '%')) and i.isAvailable = :isAvailable " +
           "or upper(i.description) = upper(:description) and i.isAvailable = :isAvailable1")
    List<Item> findAllByNameAndAvailableOrDescriptionAndAvailable(String name, Boolean isAvailable, String description, Boolean isAvailable1);
}
