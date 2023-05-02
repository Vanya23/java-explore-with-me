package ru.practicum.ewm.controller.pub.comments;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.controller.pub.GeneratePageableObj;
import ru.practicum.ewm.controller.pub.comments.dto.CommentInDto;
import ru.practicum.ewm.controller.pub.comments.dto.CommentMapper;
import ru.practicum.ewm.controller.pub.comments.dto.CommentOutDto;
import ru.practicum.ewm.controller.pub.comments.model.Comment;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.events.EventRepository;
import ru.practicum.ewm.controller.pub.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    CommentRepository repository;
    EventRepository eventRepository;
    UserRepository userRepository;
    CommentMapper commentMapper;
    GeneratePageableObj myServicePage;

    Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id"); // по возрастанию


    @Override
    @Transactional
    public CommentOutDto addComment(long userId, long eventId, CommentInDto commentDto) {
        Comment comment = commentMapper.fromCommentsInDtoToComment(commentDto);
        comment.setCreated(LocalDateTime.now());
        checkComment(userId, eventId, comment.getText());
        comment.setUser(userRepository.getReferenceById(userId));
        comment.setEvent(eventRepository.getReferenceById(eventId));
        repository.saveAndFlush(comment);
        return commentMapper.fromCommentsToCommentOutDto(comment);
    }

    @Override
    @Transactional
    public void deleteByUser(long userId) {
        repository.deleteCommentByUser_Id(userId);
    }

    @Override
    @Transactional
    public void deleteByEvent(long eventId) {
        repository.deleteCommentByEvent_Id(eventId);
    }

    @Override
    @Transactional
    public void deleteById(long commentId) {
        repository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentOutDto patchComment(long commentId, CommentInDto commentDto) {
        if (!repository.existsById(commentId) || Strings.isBlank(commentDto.getText()))
            throw new BadRequestException("");
        Comment comment = repository.getReferenceById(commentId);
        comment.setText(commentDto.getText());
        comment.setPatched(LocalDateTime.now());
        repository.saveAndFlush(comment);
        return commentMapper.fromCommentsToCommentOutDto(comment);
    }


    @Override
    public CommentOutDto getCommentsById(long commentId) {
        if (!repository.existsById(commentId)) throw new BadRequestException("");
        return commentMapper.fromCommentsToCommentOutDto(repository.getReferenceById(commentId));
    }

    @Override
    public List<CommentOutDto> getCommentsByUserAndEvents(List<Long> idsu, List<Long> idse, int from, int size) {
        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);

        Page<Comment> page;
        if (idsu.size() == 0 && idse.size() == 0) page = repository.findAll(pageable);
        else page = repository.findByUserInAndEvent_IdIn(idsu, idse, pageable);

        List<Comment> comments = page.getContent();

        return commentMapper.fromCommentsListToCommentOutDtoList(comments);
    }


    private void checkComment(long userId, long eventId, String text) {
        if (!eventRepository.existsById(eventId) || !userRepository.existsById(userId) || Strings.isBlank(text)) {
            throw new BadRequestException("");
        }
    }


}
