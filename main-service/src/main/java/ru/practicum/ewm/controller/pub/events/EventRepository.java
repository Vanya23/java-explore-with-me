package ru.practicum.ewm.controller.pub.events;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.controller.pub.enums.EventState;
import ru.practicum.ewm.controller.pub.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAll(Pageable pageable);


    Page<Event> findAllByInitiator_Id(long userId, Pageable pageable);

    Event findByIdAndInitiator_Id(long eventId, long userId);


    Page<Event> findAllByInitiator_IdInAndStateevnInAndCategory_IdInAndEventDateBetween(
            List<Long> users, List<EventState> states, List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd, Pageable pageable);

//    @Query(
//            value = "SELECT * FROM events ORDER BY id",
//            countQuery = "SELECT count(*) FROM events",
//            nativeQuery = true)
//    Page<Event> searchEvent77(Pageable pageable);


    @Query(value = "select * from events " +
            " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(annotation) LIKE lower(concat('%', ?1, '%')))" +
            "and paid = ?2 and event_date between ?3 and ?4 and id_category in ?5",
            countQuery = "select count(*) from events " +
                    " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(annotation) LIKE lower(concat('%', ?1, '%')))" +
                    "and paid = ?2 and event_date between ?3 and ?4 and id_category in ?5" +
                    " ",
            nativeQuery = true)
    Page<Event> searchEvent(String textForFind, Boolean paid, LocalDateTime start, LocalDateTime end, List<Long> categories, Pageable pageable);


    @Query(value = "select * from events " +
            " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(annotation) LIKE lower(concat('%', ?1, '%')))" +
            "and paid = ?2 and event_date between ?3 and ?4 and id_category in ?5" +
            " ;", nativeQuery = true)
    List<Event> searchEvent44(String textForFind, boolean paid, LocalDateTime start, LocalDateTime end, List<Long> categories);

    @Query(value = "select * from events " +
            " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(annotation) LIKE lower(concat('%', ?1, '%')))" +
            "and paid = ?2 and event_date between ?3 and ?4 and id_category in ?5" + " and participant_limit > confirmed_requests",
            countQuery = "select count(*) from events " +
                    " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(annotation) LIKE lower(concat('%', ?1, '%')))" +
                    "and paid = ?2 and event_date between ?3 and ?4 and id_category in ?5" +
                    " and participant_limit > confirmed_requests ",
            nativeQuery = true)
    Page<Event> searchEventLimit(String textForFind, Boolean paid, LocalDateTime start, LocalDateTime end, List<Long> categories, Pageable pageable);


//    @Query(value = "select * from events " +
//            " where (lower(description) LIKE lower(concat('%', ?1, '%')) or lower(full_name) LIKE lower(concat('%', ?1, '%')))" +
//            "and available = true " +
//            " order by id;", nativeQuery = true)
//    Page<Event> searchItemByText(String textForFind, Pageable pageable);

}