package ru.practicum.ewm.controller.pub.users.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.Create;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String name;

}
