package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {
    public static Comment toNewComment(final CommentDTO commentDTO, final Item item, final User user) {
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        return comment;
    }

    public static CommentDTO toCommentDTO(final Comment comment, final Long itemId, final User user) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setItem(itemId);
        commentDTO.setAuthorName(user.getName());
        commentDTO.setCreated(comment.getCreated());
        return commentDTO;
    }

    public static List<CommentDTO> toCommentDTOList(final List<Comment> comments, final Long itemId, final User user) {
        return comments.stream().map(comment -> toCommentDTO(comment, itemId, user)).collect(Collectors.toList());
    }
}
