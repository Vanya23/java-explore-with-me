package ru.practicum.ewm.controller.pub.requests;


import ru.practicum.ewm.controller.pub.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestsService {


    ParticipationRequestDto addRequests(long eventId, long userId);

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto patchRequests(long userId, long requestId);

}
