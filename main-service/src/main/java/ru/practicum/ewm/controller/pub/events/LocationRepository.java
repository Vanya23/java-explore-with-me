package ru.practicum.ewm.controller.pub.events;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.controller.pub.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {


}