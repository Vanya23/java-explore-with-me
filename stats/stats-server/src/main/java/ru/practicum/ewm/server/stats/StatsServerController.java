package ru.practicum.ewm.server.stats;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
//@RequiredArgsConstructor
@RequestMapping
@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatsServerController {
//    StatsClient statsClient = new StatsClient(new ObjectMapper());

    //    @Autowired
    StatsClient statsClient;

    StatsServerService service;

    public StatsServerController(ObjectMapper mapper, StatsServerService service) {
        this.service = service;
        statsClient = new StatsClient(mapper);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public EndpointHit addHit(@RequestBody EndpointHit endpointHit) {
        return
                service.addHit(endpointHit);
    }

    @GetMapping(path = "/test")
    public String test() {
        return "hello world";
    }

    @GetMapping(path = "/test2")
    public String test2() {

        return "hello world2222";
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
