package ru.practicum.ewm.controller.pub.events.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.controller.pub.events.EventService;
import ru.practicum.ewm.controller.pub.events.FindSort;
import ru.practicum.ewm.controller.pub.events.dto.EventFullDto;
import ru.practicum.ewm.controller.pub.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping(path = "/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventController {

    EventService service;
    final String eventsPath = "/{id}";

    StatsClient statsClient;

    public EventController(ObjectMapper objectMapper, EventService service) {
        this.service = service;
        statsClient = new StatsClient(objectMapper);
    }


    @GetMapping
    public List<EventShortDto> getPublicEvents(
            @RequestParam(required = false, defaultValue = "") String text,
            @RequestParam(required = false, defaultValue = "") List<Long> categories,
            @RequestParam(required = false, defaultValue = "") Boolean paid,
            @RequestParam(required = false, defaultValue = "") String rangeStart,
            @RequestParam(required = false, defaultValue = "") String rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size, HttpServletRequest httpServletRequest) {
        statsClient.hit(httpServletRequest);
        if (Strings.isBlank(sort)) return new ArrayList<>();
        FindSort fs = FindSort.valueOf(sort);
        return service.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, fs, from, size, statsClient);
    }


    @GetMapping(eventsPath)
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        statsClient.hit(httpServletRequest);
        return service.getPublicEventById(id);
    }


}
