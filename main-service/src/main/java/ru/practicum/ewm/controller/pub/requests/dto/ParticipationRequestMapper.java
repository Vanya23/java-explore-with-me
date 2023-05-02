package ru.practicum.ewm.controller.pub.requests.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.events.model.EventPatternTime;
import ru.practicum.ewm.controller.pub.requests.model.ParticipationRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParticipationRequestMapper {

    EventPatternTime eventPatternTime;

    public ParticipationRequestDto fromParticipationRequestToParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getCreated().format(eventPatternTime.getFormatter()),
                participationRequest.getEvent().getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getStatus().toString());
    }

    public List<ParticipationRequestDto> fromParticipationRequestListToParticipationRequestDtoList(List<ParticipationRequest> participationRequests) {
        List<ParticipationRequestDto> participationRequestDtos = new ArrayList<>();
        for (ParticipationRequest participationRequest :
                participationRequests) {
            participationRequestDtos.add(fromParticipationRequestToParticipationRequestDto(participationRequest));
        }
        return participationRequestDtos;
    }


}
