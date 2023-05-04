package ru.practicum.ewm.controller.pub.events.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.enums.EventState;
import ru.practicum.ewm.controller.pub.location.Location;
import ru.practicum.ewm.controller.pub.category.model.Category;
import ru.practicum.ewm.controller.pub.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "annotation")
    String annotation;
    @ManyToOne
    @JoinColumn(name = "id_category")
    Category category;
    @Column(name = "confirmed_requests")
    Integer confirmedRequests;
    @Column(name = "created_on")
    LocalDateTime createdOn;
    @Column(name = "description")
    String description;
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    User initiator;
    @ManyToOne
    @JoinColumn(name = "id_location")
    Location location;
    @Column(name = "paid")
    Boolean paid;
    @Column(name = "participant_limit")
    Integer participantLimit;
    @Column(name = "published_on")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    EventState stateevn;
    @Column(name = "title")
    String title;
    @Column(name = "views")
    Integer views;

    @Transient
    Long hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
