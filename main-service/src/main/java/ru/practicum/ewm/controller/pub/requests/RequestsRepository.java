package ru.practicum.ewm.controller.pub.requests;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.controller.pub.requests.model.ParticipationRequest;

import java.util.List;

public interface RequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> getAllByRequester_Id(Long userId);

    List<ParticipationRequest> getAllByRequester_IdAndEvent_Id(Long userId, Long eventId);

    List<ParticipationRequest> getAllByIdIn(List<Long> ids);

    List<ParticipationRequest> getAllByEvent_Id(Long eventId);


    Boolean existsByRequester_IdAndEvent_Id(Long userId, Long eventId);

}