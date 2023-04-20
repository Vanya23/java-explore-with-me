package ru.practicum.ewm.server.stats;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class StatsServerController {

    private final StatsServerService service;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @PostMapping(path = "/hit")
    public EndpointHit addHit(@RequestBody EndpointHit endpointHit) {
        return
                service.addHit(endpointHit);
    }

    @GetMapping(path = "/test")
    public String test() {
        return "hello world";
    }

    @GetMapping(path = "/stats", params = {"start", "end"})
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false, defaultValue = "false") String unique,
                                    @RequestParam(required = false) List<String> uris) {

        LocalDateTime tStart = LocalDateTime.parse(start, formatter);
        LocalDateTime tEnd = LocalDateTime.parse(end, formatter);
        Boolean bUnique = Boolean.valueOf(unique);
        return service.getStats(tStart, tEnd, bUnique, uris);
    }


}
