package ru.practicum.ewm.controller.pub.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "lat")
    Double lat;
    @Column(name = "lon")
    Double lon;


}
