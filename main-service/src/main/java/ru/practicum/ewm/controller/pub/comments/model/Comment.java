package ru.practicum.ewm.controller.pub.comments.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.controller.pub.events.model.Event;
import ru.practicum.ewm.controller.pub.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;
    @ManyToOne
    @JoinColumn(name = "id_event")
    Event event;
    @Column(name = "created")
    LocalDateTime created;
    @Column(name = "patched")
    LocalDateTime patched;
    @Column(name = "text_com")
    String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        return id != null && id.equals(((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
