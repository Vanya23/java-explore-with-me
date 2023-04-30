package ru.practicum.ewm.controller.pub.events.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.events.EventService;
import ru.practicum.ewm.controller.pub.events.dto.*;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserPrivateEventController {

    EventService service;
    final String eventsPath = "/{eventId}";
    final String reqPath = "/requests";


    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable long userId,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return service.getEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public EventFullDto addEvent(@Validated({Create.class}) @RequestBody NewEventDto newEventDto,
                                 @PathVariable long userId) {
        return service.addEvent(newEventDto, userId);
    }

    @GetMapping(eventsPath)
    public EventFullDto getEventsById(@PathVariable long userId, @PathVariable long eventId) {
        return service.getEventsById(eventId, userId);
    }

    @PatchMapping(eventsPath)
    public EventFullDto patchEventsById(@PathVariable long userId, @PathVariable long eventId,
                                        @RequestBody(required = false) UpdateEventUserRequest updateEventUserRequest) {
        return service.patchEventsById(eventId, userId, updateEventUserRequest);
    }


    @GetMapping(eventsPath + reqPath)
    public List<ParticipationRequestDto> getEventsReqById(@PathVariable long userId, @PathVariable long eventId) {
        return service.getEventsReqById(eventId, userId);
    }

    @PatchMapping(eventsPath + reqPath)
    public EventRequestStatusUpdateResult patchEventsReqById(@PathVariable long userId, @PathVariable long eventId,
                                                             @RequestBody EventRequestStatusUpdateRequest request) {
        return service.patchEventsReqById(eventId, userId, request);
    }


}
