package ru.practicum.ewm.controller.pub.requests;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.controller.pub.enums.EventState;
import ru.practicum.ewm.controller.pub.enums.EventUpdateState;
import ru.practicum.ewm.controller.pub.error.exception.ConflictException;
import ru.practicum.ewm.controller.pub.error.exception.NotFoundException;
import ru.practicum.ewm.controller.pub.events.EventRepository;
import ru.practicum.ewm.controller.pub.events.model.Event;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestMapper;
import ru.practicum.ewm.controller.pub.requests.model.ParticipationRequest;
import ru.practicum.ewm.controller.pub.users.UserRepository;
import ru.practicum.ewm.controller.pub.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestsServiceImpl implements RequestsService {
    RequestsRepository repository;
    UserRepository userRepository;
    EventRepository eventRepository;
    ParticipationRequestMapper participationRequestMapper;


    @Override
    @Transactional
    public ParticipationRequestDto addRequests(long eventId, long userId) {
        if (repository.existsByRequester_IdAndEvent_Id(userId, eventId))
            throw new ConflictException(""); // нельзя добавить повторный запрос
        Event event = eventRepository.getReferenceById(eventId);
        User user = userRepository.getReferenceById(userId);
        if (user.getId().equals(event.getInitiator().getId()))
            throw new ConflictException(""); // инициатор события не может добавить запрос
        if (!event.getStateevn().equals(EventState.PUBLISHED))
            throw new ConflictException(""); //неопубликованном событии
        if (!event.getParticipantLimit().equals(0)) {
            if (event.getConfirmedRequests() >= event.getParticipantLimit())
                throw new ConflictException(""); // если у события достигнут лимит запросов на участие
        }
        ParticipationRequest participationRequest = new ParticipationRequest(
                -1L, LocalDateTime.now(), user,
                event, EventUpdateState.PENDING
        );
        if (!event.getRequestModeration()) {
            participationRequest.setStatus(EventUpdateState.CONFIRMED); // если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        participationRequest = repository.save(participationRequest);
        return participationRequestMapper.fromParticipationRequestToParticipationRequestDto(participationRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        return participationRequestMapper.fromParticipationRequestListToParticipationRequestDtoList(
                repository.getAllByRequester_Id(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto patchRequests(long userId, long requestId) {
        ParticipationRequest participationRequest = repository.getReferenceById(requestId);
        if (participationRequest.getRequester().getId() != userId) throw new NotFoundException("");
        participationRequest.setStatus(EventUpdateState.CANCELED);
        participationRequest = repository.save(participationRequest);
        return participationRequestMapper.fromParticipationRequestToParticipationRequestDto(participationRequest);
    }


}
