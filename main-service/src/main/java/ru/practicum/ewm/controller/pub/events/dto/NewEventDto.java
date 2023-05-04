package ru.practicum.ewm.controller.pub.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.location.LocationDto;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewEventDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String annotation;
    @NotBlank(groups = {Create.class})
    Long category;
    @NotBlank(groups = {Create.class})
    String description;
    @NotBlank(groups = {Create.class})
    String eventDate;
    @NotBlank(groups = {Create.class})
    LocationDto location;
    @NotBlank(groups = {Create.class})
    Boolean paid;
    Integer participantLimit = 0; // default
    Boolean requestModeration = true; // default
    @NotBlank(groups = {Create.class})
    String title;

}
