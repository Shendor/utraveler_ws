DROP DATABASE utraveler;
CREATE DATABASE utraveler;
USE utraveler;

CREATE TABLE IF NOT EXISTS user (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    password          VARCHAR(64)   NOT NULL,
    register_date     DATETIME      NOT NULL,
    name              VARCHAR(255),
    email             VARCHAR(255)  NOT NULL UNIQUE,
    description       VARCHAR(1024) NULL,
    avatar            MEDIUMBLOB    NULL,
    cover             MEDIUMBLOB    NULL,
    change_date       TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_setting (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    user_id            BIGINT       NOT NULL,
    main_color         VARCHAR(9),
    background_color   VARCHAR(9),
    text_color         VARCHAR(9),
    cover_opacity      FLOAT,
    is_landscape_cover BIT(1),
    limit_code         VARCHAR(255),
    change_date        TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS event (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    user_id           BIGINT        NOT NULL,
    name              VARCHAR(64)   NOT NULL,
    start_date        DATETIME     NOT NULL,
    end_date          DATETIME     NULL,
    image             MEDIUMBLOB    NULL,
    change_date       TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS message (
    id                BIGINT         NOT NULL AUTO_INCREMENT,
    event_id          BIGINT         NOT NULL,
    text              VARCHAR(4096)  NULL,
    date              DATETIME       NOT NULL,
    longitude         REAL,
    latitude          REAL,
    facebook_post_id  VARCHAR(255),
    change_date       TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS trip_plan (
    id                      BIGINT      NOT NULL AUTO_INCREMENT,
    event_id                BIGINT      NOT NULL,
    plan_items_json         MEDIUMTEXT  NULL,
    rent_plan_items_json    MEDIUMTEXT  NULL,
    flight_plan_items_json  MEDIUMTEXT  NULL,
    change_date             TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS money_spending (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    event_id          BIGINT        NOT NULL,
    amount            FLOAT         NOT NULL,
    date              DATETIME      NOT NULL,
    category          VARCHAR(128),
    currency          VARCHAR(64),
    description       VARCHAR(1024),
    change_date       TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS photo (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    event_id          BIGINT        NOT NULL,
    imageUrl          VARCHAR(1024) NULL,
    name              VARCHAR(255)  NOT NULL,
    description       VARCHAR(4096) NULL,
    date              DATETIME      NOT NULL,
    thumbnail         BLOB,
    longitude         REAL,
    latitude          REAL,
    width             INT,
    height            INT,
    facebook_post_id  VARCHAR(128),
    facebook_album_id VARCHAR(128),
    facebook_photo_id VARCHAR(128),
    change_date       TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS route (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    event_id          BIGINT        NOT NULL,
    description       VARCHAR(1024) NULL,
    name              VARCHAR(255)  NOT NULL,
    coordinates_json  MEDIUMTEXT    NOT NULL,
    polygons_json     MEDIUMTEXT,
    pushpins_json     MEDIUMTEXT,
    color             VARCHAR(9),
    change_date       TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS reset_password_record (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    user_id       BIGINT        NOT NULL,
    code          VARCHAR(64)   NOT NULL,
    change_date   TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS app_info (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    version       VARCHAR(8)    NOT NULL,
    change_date   TIMESTAMP,

    PRIMARY KEY (id)
);