package ru.practicum.ewm.server.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.server.stats.model.Hits;

import java.time.LocalDateTime;
import java.util.List;

public interface HitsRepository extends JpaRepository<Hits, Long> {
    List<Hits> getAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

}
