package ru.practicum.ewm.controller.pub.events.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static ru.practicum.ewm.controller.pub.MyPatternTime.myPatternTime;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventPatternTime {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(myPatternTime);
}
