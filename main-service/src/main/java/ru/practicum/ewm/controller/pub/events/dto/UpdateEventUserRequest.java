package ru.practicum.ewm.controller.pub.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.controller.pub.location.LocationDto;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateEventUserRequest {
    String annotation;
    Long category;
    String description;
    String eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String stateAction;
    String title;

}
