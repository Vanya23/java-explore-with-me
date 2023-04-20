package ru.practicum.ewm.server.stats.model;


import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.stats.EndpointHit;

@Component
public class HitsMapper {
    public EndpointHit fromHitsToEndpointHit(Hits hits) {

        return new EndpointHit(hits.getId(), hits.getApp(), hits.getUri(), hits.getIp(), hits.getTimestamp());
    }


    public Hits fromEndpointHitToHits(EndpointHit endpointHit) {
        Hits hits = new Hits();
        hits.setApp(endpointHit.getApp());
        hits.setUri(endpointHit.getUri());
        hits.setIp(endpointHit.getIp());
        hits.setTimestamp(endpointHit.getTimestamp());
        return hits;
    }


}
