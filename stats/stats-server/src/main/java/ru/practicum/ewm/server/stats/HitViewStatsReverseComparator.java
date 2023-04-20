package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.ViewStats;

import java.util.Comparator;

public class HitViewStatsReverseComparator implements Comparator<ViewStats> {

    @Override
    public int compare(ViewStats o1, ViewStats o2) {
        return -1 * o1.getHits().compareTo((o2.getHits()));
    }
}