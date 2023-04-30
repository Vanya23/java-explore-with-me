package ru.practicum.ewm.controller.pub.requests.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.EventUpdateState;
import ru.practicum.ewm.controller.pub.events.model.Event;
import ru.practicum.ewm.controller.pub.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests")

public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "created")
    LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "id_user")
    User requester;
    @ManyToOne
    @JoinColumn(name = "id_event")
    Event event;
    @Enumerated(EnumType.STRING)
    EventUpdateState status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipationRequest)) return false;
        return id != null && id.equals(((ParticipationRequest) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
