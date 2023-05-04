package ru.practicum.ewm.controller.pub.events.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.location.Location;
import ru.practicum.ewm.controller.pub.location.LocationDto;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationMapper {


    public LocationDto fromLocationToLocationDto(Location location) {

        return new LocationDto(
                location.getId(),
                location.getLat(),
                location.getLon());

    }


    public Location fromLocationDtoToLocation(LocationDto locationDto) {

        return new Location(
                locationDto.getId(),
                locationDto.getLat(),
                locationDto.getLon());

    }


}
