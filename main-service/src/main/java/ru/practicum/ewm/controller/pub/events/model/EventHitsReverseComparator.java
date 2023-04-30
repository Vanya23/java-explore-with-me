package ru.practicum.ewm.controller.pub.events.model;


import java.util.Comparator;

public class EventHitsReverseComparator implements Comparator<Event> { // сортировка по просмотрам
    @Override
    public int compare(Event o1, Event o2) {
        return -1 * o1.getHits().compareTo((o2.getHits()));
    }
}

