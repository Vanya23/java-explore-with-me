drop table IF EXISTS compilations_events;
drop table IF EXISTS compilations;
drop table IF EXISTS requests;
drop table IF EXISTS events;
drop table IF EXISTS locations;
drop table IF EXISTS categories;
drop table IF EXISTS users;
CREATE TABLE IF not EXISTS users (
                                     id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                     name_u varchar NOT NULL,
                                     email varchar NOT NULL,
                                     CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_un UNIQUE (email)
    );

CREATE TABLE IF not EXISTS categories (
                                     id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                     name_c varchar NOT NULL,
                                     CONSTRAINT categories_pk PRIMARY KEY (id),
    CONSTRAINT categories_un UNIQUE (name_c)
    );
CREATE TABLE IF not EXISTS locations (
                                     id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                     lat float8 NOT NULL,
                                     lon float8 NOT NULL,
                                     CONSTRAINT locations_pk PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS events (
                                        id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                        annotation varchar NOT NULL,
                                        id_category int8  NOT NULL,
                                        confirmed_requests int8,
                                        created_on timestamp,
                                        description varchar,
                                        event_date timestamp NOT NULL,
                                        id_user int8  NOT NULL,
                                        id_location int8 NOT NULL ,
                                        paid boolean  NOT NULL,
                                        participant_limit int8  ,
                                        published_on timestamp,
                                        request_moderation boolean  ,
                                        stateevn varchar,
                                        title varchar NOT NULL,
                                        views int8  ,
                                        CONSTRAINT events_pk PRIMARY KEY (id),
    CONSTRAINT events_fk FOREIGN KEY (id_category) REFERENCES categories (id),
    CONSTRAINT events_fk2 FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT events_fk3 FOREIGN KEY (id_location) REFERENCES locations (id)
    );
CREATE TABLE IF NOT EXISTS requests (
                                        id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                        created timestamp,
                                        id_user int8  NOT NULL,
                                        id_event int8  NOT NULL,
                                        status varchar,
                                       CONSTRAINT requests_pk PRIMARY KEY (id),
    CONSTRAINT requests_fk FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT requests_fk2 FOREIGN KEY (id_event) REFERENCES events (id)
    );
CREATE TABLE IF NOT EXISTS compilations (
                                        id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                        pinned boolean NOT NULL,
                                        title varchar  NOT NULL,
                                       CONSTRAINT compilations_pk PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS compilations_events (
                                        id_compilations int8 NOT NULL,
                                        id_event int8 NOT NULL,

    CONSTRAINT compilations_events_fk FOREIGN KEY (id_compilations) REFERENCES compilations (id),
    CONSTRAINT compilations_events_fk2 FOREIGN KEY (id_event) REFERENCES events (id),
    CONSTRAINT compilations_events_un1 UNIQUE (id_compilations,id_event)
    );