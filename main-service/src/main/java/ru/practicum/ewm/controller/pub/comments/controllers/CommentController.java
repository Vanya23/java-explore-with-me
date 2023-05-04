package ru.practicum.ewm.controller.pub.comments.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.comments.CommentService;
import ru.practicum.ewm.controller.pub.comments.dto.CommentInDto;
import ru.practicum.ewm.controller.pub.comments.dto.CommentOutDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentService service;
    final String pathFull = "/users/{userId}/events/{eventId}";
    final String pathUser = "/users/{userId}";
    final String pathEvent = "/events/{eventId}";
    final String pathComment = "/{commentId}";


    @GetMapping(value = pathComment)
    public CommentOutDto getCommentsById(@PathVariable long commentId) {

        return service.getCommentsById(commentId);
    }

    @GetMapping
    public List<CommentOutDto> getCommentsByUserAndEvents(@RequestParam(required = false, defaultValue = "") List<Long> idsu,
                                                          @RequestParam(required = false, defaultValue = "") List<Long> idse,
                                                          @RequestParam(required = false, defaultValue = "0") int from,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {

        return service.getCommentsByUserAndEvents(idsu, idse, from, size);
    }


    @PostMapping(value = pathFull)
    @ResponseStatus(HttpStatus.CREATED) // 201
    public CommentOutDto addComment(@PathVariable long userId, @PathVariable long eventId,
                                    @Validated({Create.class}) @RequestBody CommentInDto commentDto) {
        return service.addComment(userId, eventId, commentDto);
    }

    @PatchMapping(value = pathComment)
    public CommentOutDto patchComment(@PathVariable long commentId,
                                      @Validated({Create.class}) @RequestBody CommentInDto commentDto) {
        return service.patchComment(commentId, commentDto);
    }


    @DeleteMapping(value = pathUser)
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteByUser(@PathVariable long userId) {
        service.deleteByUser(userId);

    }

    @DeleteMapping(value = pathEvent)
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteByEvent(@PathVariable long eventId) {
        service.deleteByEvent(eventId);

    }

    @DeleteMapping(value = pathComment)
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable long commentId) {
        service.deleteById(commentId);

    }


}
