package ru.practicum.ewm.controller.pub.events.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.events.EventService;
import ru.practicum.ewm.controller.pub.events.dto.EventFullDto;
import ru.practicum.ewm.controller.pub.events.dto.UpdateEventAdminRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminEventController {

    EventService service;
    final String eventsPath = "/{eventId}";


    @GetMapping
    public List<EventFullDto> getAdminEvents(
            @RequestParam(required = false, defaultValue = "") List<Long> users,
            @RequestParam(required = false, defaultValue = "") List<String> states,
            @RequestParam(required = false, defaultValue = "") List<Long> categories,
            @RequestParam(required = false, defaultValue = "") String rangeStart,
            @RequestParam(required = false, defaultValue = "") String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size) {

        return service.getAdminEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(value = eventsPath)
    public EventFullDto patchAdminEvents(@PathVariable long eventId, @RequestBody UpdateEventAdminRequest updateEventAdminRequest
    ) {
        return service.patchAdminEvents(eventId, updateEventAdminRequest);
    }


}
