package ru.practicum.ewm.controller.pub.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {
    Long id;
    Double lat;
    Double lon;
}
