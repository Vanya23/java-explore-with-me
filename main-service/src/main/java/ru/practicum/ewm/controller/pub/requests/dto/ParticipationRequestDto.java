package ru.practicum.ewm.controller.pub.requests.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    Long id;
    String created;
    Long event;
    Long requester;
    String status;

}
