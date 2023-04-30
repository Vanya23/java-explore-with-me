package ru.practicum.ewm.controller.pub.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.LocationDto;
import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;
import ru.practicum.ewm.controller.pub.users.dto.UserShortDto;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventFullDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String annotation;
    @NotBlank(groups = {Create.class})
    CategoryDto category;
    Integer confirmedRequests;
    String createdOn;
    String description;
    @NotBlank(groups = {Create.class})
    String eventDate;
    @NotBlank(groups = {Create.class})
    UserShortDto initiator;
    @NotBlank(groups = {Create.class})
    LocationDto location;
    @NotBlank(groups = {Create.class})
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    String state;
    @NotBlank(groups = {Create.class})
    String title;
    Integer views;

}
