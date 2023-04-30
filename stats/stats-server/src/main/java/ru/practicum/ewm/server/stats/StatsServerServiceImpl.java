package ru.practicum.ewm.server.stats;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.server.stats.model.Hits;
import ru.practicum.ewm.server.stats.model.HitsMapper;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsServerServiceImpl implements StatsServerService {

    HitsRepository repository;
    HitsMapper hitsMapper;

    @Override
    public EndpointHit addHit(EndpointHit endpointHit) {
        return hitsMapper.fromHitsToEndpointHit(repository.save(hitsMapper.fromEndpointHitToHits(endpointHit)));

    }


    @Override
    public List<ViewStats> getStats(LocalDateTime tStart, LocalDateTime tEnd, Boolean bUnique, List<String> uris) {
        List<Hits> listSt = repository.getAllByTimestampBetween(tStart, tEnd);
        // перекомпоновываем в словарь
        Map<String, List<Hits>> mapH = new HashMap<>();
        for (Hits h :
                listSt) {
            String currentUri = h.getUri();

            if (!mapH.containsKey(currentUri)) mapH.put(currentUri, new ArrayList<>());

            mapH.get(currentUri).add(h);
        }


        if (uris == null || uris.size() == 0) { // если список пустой, значит все ключи
            uris = new ArrayList<>();
            uris.addAll(mapH.keySet());
        }

        List<ViewStats> viewStatsList = new ArrayList<>();

        for (String sUri :
                uris) {
            if (!mapH.containsKey(sUri)) continue;
            List<Hits> listStbuf = mapH.get(sUri);
            ViewStats viewStats = new ViewStats();
            viewStats.setApp(listStbuf.get(0).getApp());
            viewStats.setUri(listStbuf.get(0).getUri());
            Long hits = 0L;
            if (!bUnique) hits = (long) listStbuf.size();
            else {
                HashSet<String> uniqueS = new HashSet<>(); // c помощью set вычисляем уникальные ip
                for (Hits hb :
                        listStbuf) {
                    uniqueS.add(hb.getIp());
                }
                hits = (long) uniqueS.size();
            }
            viewStats.setHits(hits);
            viewStatsList.add(viewStats);
        }

        Collections.sort(viewStatsList, new HitViewStatsReverseComparator()); //  сортировка по убыванию количества просмотров

        return viewStatsList;
    }
}
