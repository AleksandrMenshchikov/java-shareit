package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.CreateBookingDTO;
import ru.practicum.shareit.booking.dto.ResponseBookingDTO;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ResponseBookingDTO createBooking(CreateBookingDTO createBookingDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Item item = itemRepository.findById(createBookingDTO.getItemId()).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", createBookingDTO.getItemId())));

        if (!item.getIsAvailable()) {
            throw new BadRequestException(String.format("Item '%d' is not available", createBookingDTO.getItemId()));
        }

        if (createBookingDTO.getStart().equals(createBookingDTO.getEnd())) {
            throw new BadRequestException("Start and end dates cannot be the same");
        }

        Booking newBooking = BookingMapper.toNewBooking(createBookingDTO, item, user, BookingStatus.WAITING);
        Booking saved = bookingRepository.save(newBooking);
        return BookingMapper.toResponseBookingDTO(saved);
    }

    @Transactional
    public ResponseBookingDTO updateBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Booking with id '%d' not found", bookingId)));
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", booking.getItem().getId())));

        if (!Objects.equals(userId, item.getOwner().getId())) {
            throw new ForbiddenRequestException("You do not own this item");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingMapper.toResponseBookingDTO(booking);
    }

    @Transactional(readOnly = true)
    public ResponseBookingDTO getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Booking with id '%d' not found", bookingId)));
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", booking.getItem().getId())));

        if (Objects.equals(userId, item.getOwner().getId()) || Objects.equals(userId, booking.getBooker().getId())) {
            return BookingMapper.toResponseBookingDTO(booking);
        } else {
            throw new BadRequestException("You do not booker this booking or you do not own this item");
        }
    }

    @Transactional(readOnly = true)
    public List<ResponseBookingDTO> getUserBookings(Long userId, String state) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));

        if (state == null) {
            List<Booking> bookings = bookingRepository.findAllByBooker_Id(userId);
            return BookingMapper.toResponseBookingDTOList(bookings);
        }

        for (BookingStatus value : BookingStatus.values()) {
            if (state.toUpperCase().equals(value.toString())) {
                List<Booking> bookings = bookingRepository.findAllByBookerIdAndStatus(userId, value);
                return BookingMapper.toResponseBookingDTOList(bookings);
            }
        }

        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<ResponseBookingDTO> getOwnerBookings(Long userId, String state) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));

        if (state == null) {
            List<Booking> bookings = bookingRepository.findAllByBookerIdJoiningItem(userId);
            return BookingMapper.toResponseBookingDTOList(bookings);
        }

        for (BookingStatus value : BookingStatus.values()) {
            if (state.toUpperCase().equals(value.toString())) {
                List<Booking> bookings = bookingRepository.findAllByBookerIdAndStatusJoiningItem(userId, value);
                return BookingMapper.toResponseBookingDTOList(bookings);
            }
        }

        return new ArrayList<>();
    }
}
