package ru.practicum.ewm.controller.pub.events;


import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.controller.pub.events.dto.*;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {


    EventFullDto addEvent(NewEventDto newEventDto, Long userId);

    List<EventShortDto> getEvents(long userId, int from, int size);

    EventFullDto getEventsById(long eventId, long userId);

    EventFullDto patchEventsById(long eventId, long userId, UpdateEventUserRequest updateEventUserRequest);

    List<EventFullDto> getAdminEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size);

    EventFullDto patchAdminEvents(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    List<ParticipationRequestDto> getEventsReqById(long eventId, long userId);

    EventRequestStatusUpdateResult patchEventsReqById(long eventId, long userId, EventRequestStatusUpdateRequest request);

    EventFullDto getPublicEventById(long eventId);

    List<EventShortDto> getPublicEvents(String text, List<Long> categories, Boolean paid,
                                        String rangeStart, String rangeEnd, Boolean onlyAvailable, FindSort fs, int from, int size,
                                        StatsClient statsClient);
}
