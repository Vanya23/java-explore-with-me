package ru.practicum.ewm.controller.pub.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.Create;

import javax.validation.constraints.NotBlank;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    Long id;
    List<Long> events;
    Boolean pinned = false;
    @NotBlank(groups = {Create.class})
    String title;

}
