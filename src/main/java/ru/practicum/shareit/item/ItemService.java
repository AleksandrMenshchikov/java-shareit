package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ResponseItemDTO;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final RequestRepository requestRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));

        Long requestId = itemDTO.getRequestId();

        Request request = null;
        if (requestId != null) {
            request = requestRepository.findById(requestId).orElseThrow(() ->
                    new NotFoundException(String.format("Request with id '%d' not found", requestId)));
        }

        Item saved = itemRepository.save(ItemMapper.toNewItem(itemDTO, user, request));
        return ItemMapper.toItemDTO(saved);
    }

    @Transactional
    public ItemDTO updateItem(ItemDTO itemDTO, Long userId, Long itemId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", itemId)));

        if (itemDTO.getName() == null || itemDTO.getName().isBlank()) {
            itemDTO.setName(item.getName());
        }

        if (itemDTO.getDescription() == null || itemDTO.getDescription().isBlank()) {
            itemDTO.setDescription(item.getDescription());
        }

        if (itemDTO.getAvailable() == null) {
            itemDTO.setAvailable(item.getIsAvailable());
        }

        Item saved = itemRepository.save(ItemMapper.toItem(itemDTO, itemId, item.getOwner(), item.getRequest()));
        return ItemMapper.toItemDTO(saved);
    }

    public ResponseItemDTO getItem(Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Item with id '%d' not found", itemId)));
        List<Comment> comments = commentRepository.findCommentByItem_Id(itemId);
        List<CommentDTO> commentDTOList = CommentMapper.toCommentDTOList(comments, itemId, user);
        return ItemMapper.toResponseItemDTO(item, commentDTOList);
    }

    public List<ItemDTO> getItems(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        List<Item> items = itemRepository.findItemsByOwner_Id(userId);
        return ItemMapper.toItemDTOList(items);
    }

    public List<ItemDTO> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }

        List<Item> list = itemRepository.findAllByNameAndAvailableOrDescriptionAndAvailable(text, true, text, true);
        return ItemMapper.toItemDTOList(list);
    }

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO, Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id '%d' not found", userId)));
        List<Booking> list = bookingRepository.findBookingByBooker_IdAndItem_IdAndEndDateBefore(userId, itemId, LocalDateTime.now());

        if (list.isEmpty()) {
            throw new BadRequestException("A comment can only be left by the user who rented the item, and only after the rental period has ended");
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Item with id '%d' not found", itemId)));

        Comment saved = commentRepository.save(CommentMapper.toNewComment(commentDTO, item, user));
        return CommentMapper.toCommentDTO(saved, itemId, user);
    }
}
