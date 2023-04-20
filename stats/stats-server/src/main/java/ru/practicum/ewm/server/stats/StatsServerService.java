package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerService {
    EndpointHit addHit(EndpointHit endpointHit);


    List<ViewStats> getStats(LocalDateTime tStart, LocalDateTime tEnd, Boolean bUnique, List<String> uris);
}
