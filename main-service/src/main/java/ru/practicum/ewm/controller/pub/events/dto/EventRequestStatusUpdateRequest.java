package ru.practicum.ewm.controller.pub.events.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    String status;
}
