package ru.practicum.ewm.controller.pub.events.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;
import ru.practicum.ewm.controller.pub.users.dto.UserShortDto;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String annotation;
    @NotBlank(groups = {Create.class})
    CategoryDto category;
    Integer confirmedRequests;
    @NotBlank(groups = {Create.class})
    String eventDate;
    @NotBlank(groups = {Create.class})
    UserShortDto initiator;
    @NotBlank(groups = {Create.class})
    Boolean paid;
    @NotBlank(groups = {Create.class})
    String title;
    Integer views;

}
