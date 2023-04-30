package ru.practicum.ewm.client.stats;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.dto.stats.ViewsStatsRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Component
public class StatsClient {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String application;
    String statsServiceUri;
    ObjectMapper json;
    HttpClient httpClient;

    public StatsClient(ObjectMapper json) {
        this.json = json;
        application = "ewm-main-service";
//        statsServiceUri = "http://localhost:9090";
        statsServiceUri = "http://ewm-stats-server:9090";
        httpClient = HttpClient.newBuilder().build();

    }

    public void hit(HttpServletRequest userRequset) {
        EndpointHit hit = EndpointHit.builder()
                .app(application)
                .ip(userRequset.getRemoteAddr())
                .uri(userRequset.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json.writeValueAsString(hit));
            HttpRequest hitRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/hit"))
                    .POST(bodyPublisher)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .build();

            HttpResponse<Void> response = httpClient.send(hitRequest, HttpResponse.BodyHandlers.discarding());
            log.debug("responce from stats-service: {}", response);
        } catch (Exception e) {
            log.warn("responce from stats-service: {}", e.getMessage());
        }


    }

    public List<ViewStats> getStats(ViewsStatsRequest request) {
        List<ViewStats> list = new ArrayList<>();
        try {
            HttpRequest statRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/stats" + "?start=1980-05-05 00:00:00&end=2055-05-05 00:00:00"))
                    .GET()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(statRequest, HttpResponse.BodyHandlers.ofString());

                    list = json.readValue((response.body()), new TypeReference<>(){});

            log.debug("responce from stats-service: {}", response);
        } catch (Exception e) {
            log.warn("responce from stats-service: {}", e.getMessage());
        }
        return list;
    }

    private String toQueryString(ViewsStatsRequest request) {
        return null;
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }


}
