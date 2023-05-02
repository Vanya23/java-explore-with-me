package ru.practicum.ewm.controller.pub.events;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.controller.pub.*;
import ru.practicum.ewm.controller.pub.category.CategoryRepository;
import ru.practicum.ewm.controller.pub.category.model.Category;
import ru.practicum.ewm.controller.pub.enums.EventState;
import ru.practicum.ewm.controller.pub.enums.EventStateAdmin;
import ru.practicum.ewm.controller.pub.enums.EventUpdateState;
import ru.practicum.ewm.controller.pub.enums.EventUpdateUserState;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.error.exception.ConflictException;
import ru.practicum.ewm.controller.pub.error.exception.NotFoundException;
import ru.practicum.ewm.controller.pub.events.dto.*;
import ru.practicum.ewm.controller.pub.events.model.Event;
import ru.practicum.ewm.controller.pub.events.model.EventHitsReverseComparator;
import ru.practicum.ewm.controller.pub.events.model.EventPatternTime;
import ru.practicum.ewm.controller.pub.location.Location;
import ru.practicum.ewm.controller.pub.requests.RequestsRepository;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestMapper;
import ru.practicum.ewm.controller.pub.requests.model.ParticipationRequest;
import ru.practicum.ewm.controller.pub.users.UserRepository;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.dto.stats.ViewsStatsRequest;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {
    EventRepository repository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    LocationRepository locationRepository;

    RequestsRepository requestsRepository;
    EventMapper eventMapper;
    ParticipationRequestMapper requestMapper;
    GeneratePageableObj myServicePage;
    EventPatternTime eventPatternTime;
    LocationMapper locationMapper;

//    StatsClient statsClient;

    Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id"); // по возрастанию
    Sort eventDateAsc = Sort.by(Sort.Direction.ASC, "event_date"); // по возрастанию


    @Override
    @Transactional
    public EventFullDto addEvent(NewEventDto newEventDto, Long userId) {
        checkNewEvenDto(newEventDto);

        checkStartTime(newEventDto.getEventDate()); // проверка начала event

        Event event = eventMapper.fromNewEventDtoToEvent(newEventDto, categoryRepository);
        event.setInitiator(userRepository.getReferenceById(userId));
        event.setLocation(locationRepository.save(
                locationMapper.fromLocationDtoToLocation(newEventDto.getLocation())));
        // status Pending

        event = repository.save(event);

        return eventMapper.fromEventToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEvents(long userId, int from, int size) {
        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);


        Page<Event> page = repository.findAllByInitiator_Id(userId, pageable);

        List<Event> events = page.getContent();

        return eventMapper.fromListEventToListEventShortDto(events);
    }

    @Override
    public EventFullDto getEventsById(long eventId, long userId) {
        return eventMapper.fromEventToEventFullDto(repository.findByIdAndInitiator_Id(eventId, userId));
    }

    @Override
    @Transactional
    public EventFullDto patchEventsById(long eventId, long userId, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest == null) {
            return new EventFullDto();
        }
        checkStartTime(updateEventUserRequest.getEventDate());
        Event event = repository.getReferenceById(eventId);
        if (event.getStateevn() == EventState.PUBLISHED) throw new ConflictException("");
        if (event.getInitiator().getId() != userId) throw new BadRequestException("");
        if (Strings.isNotBlank(updateEventUserRequest.getAnnotation())) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null
                && !event.getCategory().getId().equals(updateEventUserRequest.getCategory())) {
            Category category = categoryRepository.getReferenceById(updateEventUserRequest.getCategory());
            event.setCategory(category);
        }
        if (Strings.isNotBlank(updateEventUserRequest.getDescription())) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (Strings.isNotBlank(updateEventUserRequest.getEventDate())) {
            event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate(), eventPatternTime.getFormatter()));
        }

        // location
        if (updateEventUserRequest.getLocation() != null) {
            Location location = locationRepository.save(locationMapper.fromLocationDtoToLocation(updateEventUserRequest.getLocation()));
            event.setLocation(location);
        }

        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (Strings.isNotBlank(updateEventUserRequest.getStateAction())) {
            EventUpdateUserState eventUpdateUserState = EventUpdateUserState.valueOf(updateEventUserRequest.getStateAction());
            if (eventUpdateUserState.equals(EventUpdateUserState.SEND_TO_REVIEW)) {
                event.setStateevn(EventState.PENDING);
            } else event.setStateevn(EventState.CANCELED);
        }
        if (Strings.isNotBlank(updateEventUserRequest.getTitle())) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        repository.save(event);
        return eventMapper.fromEventToEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getAdminEvents(List<Long> users, List<String> states,
                                             List<Long> categories, String rangeStart,
                                             String rangeEnd, int from, int size) {
        LocalDateTime rangeStartLt = LocalDateTime.now();
        LocalDateTime rangeEndLt = LocalDateTime.now();
        if (Strings.isNotBlank(rangeStart)) {
            rangeStartLt = LocalDateTime.parse(rangeStart, eventPatternTime.getFormatter());
            rangeEndLt = LocalDateTime.parse(rangeEnd, eventPatternTime.getFormatter());
        }

        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);
        List<Event> events;

        List<EventState> statesSt = new ArrayList<>();
        for (String s :
                states) {

            try {
                statesSt.add(EventState.valueOf(s));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("");
            }
        }
        if (users.size() != 0 && states.size() != 0 && categories.size() != 0) {
            Page<Event> page = repository.findAllByInitiator_IdInAndStateevnInAndCategory_IdInAndEventDateBetween(users, statesSt,
                    categories, rangeStartLt, rangeEndLt, pageable);

            events = page.getContent();
        } else {

            events = new ArrayList<>();
        }

        return eventMapper.fromEventListToEventFullDtoList(events);
    }

    @Override
    @Transactional
    public EventFullDto patchAdminEvents(long eventId, UpdateEventAdminRequest updateEARequest) {
        checkStartTime(updateEARequest.getEventDate());
        Event event = repository.getReferenceById(eventId);
        if (Strings.isNotBlank(updateEARequest.getAnnotation())) {
            event.setAnnotation(updateEARequest.getAnnotation());
        }
        if (updateEARequest.getCategory() != null
                && !event.getCategory().getId().equals(updateEARequest.getCategory())) {
            Category category = categoryRepository.getReferenceById(updateEARequest.getCategory());
            event.setCategory(category);
        }
        if (Strings.isNotBlank(updateEARequest.getDescription())) {
            event.setDescription(updateEARequest.getDescription());
        }
        if (Strings.isNotBlank(updateEARequest.getEventDate())) {
            event.setEventDate(LocalDateTime.parse(updateEARequest.getEventDate(), eventPatternTime.getFormatter()));
        }
        // location
        if (updateEARequest.getLocation() != null) {
            Location location = locationRepository.save(
                    locationMapper.fromLocationDtoToLocation(updateEARequest.getLocation()));
            event.setLocation(location);
        }

        if (updateEARequest.getPaid() != null) {
            event.setPaid(updateEARequest.getPaid());
        }
        if (updateEARequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEARequest.getParticipantLimit());
        }
        if (updateEARequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEARequest.getRequestModeration());
        }
        if (Strings.isNotBlank(updateEARequest.getStateAction())) {
            EventStateAdmin current = EventStateAdmin.valueOf(updateEARequest.getStateAction());
            if (current.equals(EventStateAdmin.PUBLISH_EVENT)) {
                if (event.getStateevn().equals(EventState.PENDING)) {
                    event.setStateevn(EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                } else throw new ConflictException("");
            } else {
                if (event.getStateevn().equals(EventState.PUBLISHED)) {
                    throw new ConflictException("");
                } else event.setStateevn(EventState.CANCELED);
            }
        }

        if (Strings.isNotBlank(updateEARequest.getTitle())) {
            event.setTitle(updateEARequest.getTitle());
        }
        repository.save(event);
        return eventMapper.fromEventToEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventsReqById(long eventId, long userId) {
        if (!repository.getReferenceById(eventId).getInitiator().getId().equals(userId))
            throw new BadRequestException("");
        List<ParticipationRequest> requests = requestsRepository.getAllByEvent_Id(eventId);
        return requestMapper.fromParticipationRequestListToParticipationRequestDtoList(requests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult patchEventsReqById(long eventId, long id, EventRequestStatusUpdateRequest request) {

        if (!repository.existsById(eventId) || !userRepository.existsById(id)) throw new NotFoundException("");

        EventUpdateState eventUpdateState = EventUpdateState.valueOf(request.getStatus());
        List<Long> ids = request.getRequestIds();
        List<ParticipationRequest> requests = requestsRepository.getAllByIdIn(ids);
        Event event = repository.getReferenceById(eventId);
        if (event.getConfirmedRequests().equals(event.getParticipantLimit())) throw new ConflictException("");

        for (ParticipationRequest p :
                requests) {
            if (event.getConfirmedRequests().equals(event.getParticipantLimit())) throw new ConflictException("");
            // нельзя подтвердить заявку,
            // если уже достигнут лимит по заявкам на данное событи
            if (!p.getStatus().equals(EventUpdateState.PENDING)) throw new ConflictException("");
            //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
            if (eventUpdateState.equals(EventUpdateState.CONFIRMED)) {
                p.setStatus(EventUpdateState.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            } else {
                p.setStatus(EventUpdateState.REJECTED);
            }
        }
        List<ParticipationRequestDto> confirm = new ArrayList<>();
        List<ParticipationRequestDto> reject = new ArrayList<>();
        for (ParticipationRequest p :
                requests) {
            if (p.getStatus().equals(EventUpdateState.CONFIRMED)) {
                confirm.add(requestMapper.fromParticipationRequestToParticipationRequestDto(p));
            } else reject.add(requestMapper.fromParticipationRequestToParticipationRequestDto(p));
        }
        EventRequestStatusUpdateResult requestStatusUpdateResult = new EventRequestStatusUpdateResult(
                confirm, reject
        );

        return requestStatusUpdateResult;
    }

    @Override
    public EventFullDto getPublicEventById(long eventId) {
        return eventMapper.fromEventToEventFullDto(repository.getReferenceById(eventId));
    }

    @Override
    public List<EventShortDto> getPublicEvents(String text, List<Long> categories,
                                               Boolean paid, String rangeStart, String rangeEnd,
                                               Boolean onlyAvailable, FindSort fs, int from, int size, StatsClient statsClient) {
        LocalDateTime start;
        LocalDateTime end;
        if (Strings.isBlank(rangeStart)) {
            start = LocalDateTime.now();
            end = LocalDateTime.now().plusYears(100);
        } else {
            start = LocalDateTime.parse(rangeStart, eventPatternTime.getFormatter());
            end = LocalDateTime.parse(rangeEnd, eventPatternTime.getFormatter());
        }
        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, eventDateAsc);
        Page<Event> events;
        if (onlyAvailable) events = repository.searchEventLimit(text, paid, start, end, categories, pageable);
        else {
            events = repository.searchEvent(text, paid, start, end, categories, pageable);
        }

        List<Event> search = events.getContent();
        if (fs.equals(FindSort.VIEWS)) {
            ViewsStatsRequest viewsStatsRequest = new ViewsStatsRequest(new ArrayList<>());
            String basePath = "/events/";
            for (Event ev :
                    search) {
                viewsStatsRequest.getUris().add(basePath + ev.getId());
            }
            List<ViewStats> viewStatsList = statsClient.getStats(viewsStatsRequest);

            Map<Long, Event> eventMap = new HashMap<>();
            for (Event ev :
                    search) {
                eventMap.put(ev.getId(), ev);
            }
            for (ViewStats vs :
                    viewStatsList) {
                String numb = vs.getUri().replace(basePath, "");
                Long numbId = Long.valueOf(numb);
                if (eventMap.containsKey(numbId)) {
                    eventMap.get(numbId).setHits(
                            eventMap.get(numbId).getHits() + vs.getHits()
                    );
                }
            }

            Collections.sort(search, new EventHitsReverseComparator());

        }
        return eventMapper.fromListEventToListEventShortDto(search);
    }

    private void checkStartTime(LocalDateTime start) {
        if (LocalDateTime.now().isAfter(start.minusHours(2))) throw new ConflictException("");
    }

    private void checkStartTime(String start) {
        if (Strings.isBlank(start)) return;
        checkStartTime(LocalDateTime.parse(start, eventPatternTime.getFormatter()));
    }

    private void checkNewEvenDto(NewEventDto newEventDto) {
        if (Strings.isBlank(newEventDto.getAnnotation()) ||
                newEventDto.getCategory() == null || Strings.isBlank(newEventDto.getDescription()) ||
                Strings.isBlank(newEventDto.getEventDate())
                || newEventDto.getLocation() == null || Strings.isBlank(newEventDto.getTitle()))
            throw new BadRequestException("");
    }
}
