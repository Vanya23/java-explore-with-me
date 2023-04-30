package ru.practicum.ewm.controller.pub.events.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.EventState;
import ru.practicum.ewm.controller.pub.category.CategoryRepository;
import ru.practicum.ewm.controller.pub.category.dto.CategoryMapper;
import ru.practicum.ewm.controller.pub.events.model.Event;
import ru.practicum.ewm.controller.pub.events.model.EventPatternTime;
import ru.practicum.ewm.controller.pub.users.dto.UserMapper;
import ru.practicum.ewm.controller.pub.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventMapper {
    UserMapper userMapper;
    CategoryMapper categoryMapper;

    EventPatternTime eventPatternTime;

    LocationMapper locationMapper;


    public EventFullDto fromEventToEventFullDto(Event event) {

        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                categoryMapper.fromCategoryToCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(eventPatternTime.getFormatter()),
                event.getDescription(),
                event.getEventDate().format(eventPatternTime.getFormatter()),
                userMapper.fromUserToUserShortDto(event.getInitiator()),
                locationMapper.fromLocationToLocationDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().format(eventPatternTime.getFormatter())
                        : null,
                event.getRequestModeration(),
                event.getStateevn().name(),
                event.getTitle(),
                event.getViews()
        );
    }

    public List<EventFullDto> fromEventListToEventFullDtoList(List<Event> events) {
        List<EventFullDto> fullDtos = new ArrayList<>();
        for (Event event :
                events) {
            fullDtos.add(fromEventToEventFullDto(event));
        }
        return fullDtos;
    }

    public Event fromNewEventDtoToEvent(NewEventDto newEventDto, CategoryRepository categoryRepository) {
        return new Event(
                newEventDto.getId(),
                newEventDto.getAnnotation(),
                categoryRepository.getReferenceById(newEventDto.getCategory()),
                Integer.valueOf(0),
                LocalDateTime.now(),
                newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(), eventPatternTime.getFormatter()),
                new User(),
                locationMapper.fromLocationDtoToLocation(newEventDto.getLocation()),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                EventState.PENDING,
                newEventDto.getTitle(),
                Integer.valueOf(0), 0L);


    }


    public EventShortDto fromEventToEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                categoryMapper.fromCategoryToCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate().format(eventPatternTime.getFormatter()),
                userMapper.fromUserToUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()

        );
    }

    public List<EventShortDto> fromListEventToListEventShortDto(List<Event> events) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event event :
                events) {
            eventShortDtoList.add(fromEventToEventShortDto(event));
        }
        return eventShortDtoList;
    }


}
