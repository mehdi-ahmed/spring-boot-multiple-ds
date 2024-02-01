DROP TABLE IF EXISTS Post; -- only for the demo to clean the DB on restart. !! NOT TO DO ON PROD !!

CREATE TABLE Post
(
    id           VARCHAR(255) NOT NULL,
    title        VARCHAR(255) NOT NULL,
    slug         VARCHAR(255) NOT NULL,
    date         date         NOT NULL,
    time_to_read INT          NOT NULL,
    tags         VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)

);

INSERT INTO Post
(id,  title, slug, date, time_to_read, tags)
VALUES(1, 'Hello, bitches !', 'hello-biatches', CURRENT_DATE, 5, 'Spring Boot, Java');