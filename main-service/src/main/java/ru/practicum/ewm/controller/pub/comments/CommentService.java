package ru.practicum.ewm.controller.pub.comments;


import ru.practicum.ewm.controller.pub.comments.dto.CommentInDto;
import ru.practicum.ewm.controller.pub.comments.dto.CommentOutDto;

import java.util.List;

public interface CommentService {


    CommentOutDto addComment(long userId, long eventId, CommentInDto commentDto);

    void deleteByUser(long userId);

    void deleteByEvent(long eventId);

    void deleteById(long commentId);


    CommentOutDto patchComment(long commentId, CommentInDto commentDto);


    CommentOutDto getCommentsById(long commentId);

    List<CommentOutDto> getCommentsByUserAndEvents(List<Long> idsu, List<Long> idse, int from, int size);
}
