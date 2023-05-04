package ru.practicum.ewm.controller.pub.comments.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.controller.pub.Create;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentInDto extends CommentOutDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String text;

}
