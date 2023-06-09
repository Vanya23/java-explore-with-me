drop table IF EXISTS hits;
CREATE TABLE IF not EXISTS hits (
                                    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                    app varchar NOT NULL,
                                    uri varchar NOT NULL,
                                    ip varchar NOT NULL,
                                    time_stamp timestamp NOT NULL,
                                    CONSTRAINT endpoint_hit_pk PRIMARY KEY (id)
    );