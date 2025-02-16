package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookerId;
import ru.practicum.shareit.booking.dto.CreateBookingDTO;
import ru.practicum.shareit.booking.dto.ItemIdName;
import ru.practicum.shareit.booking.dto.ResponseBookingDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {
    public static Booking toNewBooking(final CreateBookingDTO createBookingDTO, final Item item, User user, BookingStatus status) {
        Booking booking = new Booking();
        booking.setStartDate(createBookingDTO.getStart());
        booking.setEndDate(createBookingDTO.getEnd());
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(status);
        return booking;
    }

    public static ResponseBookingDTO toResponseBookingDTO(final Booking booking) {
        ItemIdName itemIdName = new ItemIdName();
        itemIdName.setId(booking.getItem().getId());
        itemIdName.setName(booking.getItem().getName());

        BookerId bookerId = new BookerId();
        bookerId.setId(booking.getBooker().getId());

        ResponseBookingDTO responseBookingDTO = new ResponseBookingDTO();
        responseBookingDTO.setId(booking.getId());
        responseBookingDTO.setStart(booking.getStartDate());
        responseBookingDTO.setEnd(booking.getEndDate());
        responseBookingDTO.setItem(itemIdName);
        responseBookingDTO.setBooker(bookerId);
        responseBookingDTO.setStatus(booking.getStatus());

        return responseBookingDTO;
    }

    public static List<ResponseBookingDTO> toResponseBookingDTOList(final List<Booking> bookings) {
        List<ResponseBookingDTO> responseBookingDTOList = new ArrayList<>();
        for (Booking booking : bookings) {
            responseBookingDTOList.add(toResponseBookingDTO(booking));
        }
        return responseBookingDTOList;
    }
}
