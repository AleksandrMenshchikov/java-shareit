package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByBooker_Id(Long bookerId);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status);

    @Query("SELECT b FROM Booking b JOIN FETCH b.item WHERE b.item.id = :userId")
    List<Booking> findAllByBookerIdJoiningItem(Long userId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.item WHERE b.item.id = :userId and b.status = :status")
    List<Booking> findAllByBookerIdAndStatusJoiningItem(Long userId, BookingStatus status);

    List<Booking> findBookingByBooker_IdAndItem_IdAndEndDateBefore(Long bookerId, Long itemId, LocalDateTime endDateBefore);
}
