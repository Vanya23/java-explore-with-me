package ru.practicum.ewm.client.stats;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
import java.util.List;


@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class StatsClient {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String application;
    String statsServiceUri;
    ObjectMapper json;
    HttpClient httpClient;

    public StatsClient(ObjectMapper json) {
        this.json = json;
        application = "";
        statsServiceUri = "";
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
        return null;
    }

    private String toQueryString(ViewsStatsRequest request) {
        return null;
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }


}
