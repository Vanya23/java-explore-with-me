package ru.practicum.ewm.controller.pub.comments.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.category.dto.CategoryMapper;
import ru.practicum.ewm.controller.pub.comments.model.Comment;
import ru.practicum.ewm.controller.pub.events.dto.LocationMapper;
import ru.practicum.ewm.controller.pub.events.model.EventPatternTime;
import ru.practicum.ewm.controller.pub.users.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentMapper {
    UserMapper userMapper;
    CategoryMapper categoryMapper;

    EventPatternTime eventPatternTime;

    LocationMapper locationMapper;


    public CommentOutDto fromCommentsToCommentOutDto(Comment comments) {

        return new CommentOutDto(
                comments.getId(),
                comments.getUser().getId(),
                comments.getEvent().getId(),
                comments.getCreated().format(eventPatternTime.getFormatter()),
                comments.getText()

        );
    }

    public List<CommentOutDto> fromCommentsListToCommentOutDtoList(List<Comment> comments) {
        List<CommentOutDto> commentDtos = new ArrayList<>();
        for (Comment comment :
                comments) {
            commentDtos.add(fromCommentsToCommentOutDto(comment));
        }
        return commentDtos;
    }

    public Comment fromCommentsInDtoToComment(CommentInDto commentDto) {
        return new Comment(
                commentDto.getId(),
                null,
                null,
                null,
                null,
                commentDto.getText());


    }


}
