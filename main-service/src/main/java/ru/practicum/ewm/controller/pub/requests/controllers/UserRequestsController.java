package ru.practicum.ewm.controller.pub.requests.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.requests.RequestsService;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserRequestsController {

    RequestsService service;


    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId) {

        return service.getRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ParticipationRequestDto addRequests(
            @RequestParam long eventId,
            @PathVariable long userId) {

        return service.addRequests(eventId, userId);
    }

    @PatchMapping("{requestId}/cancel")
    public ParticipationRequestDto patchRequests(@PathVariable long userId, @PathVariable long requestId) {
        return service.patchRequests(userId, requestId);
    }


}
