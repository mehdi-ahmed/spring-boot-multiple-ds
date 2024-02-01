DROP TABLE IF EXISTS Subsriber; -- only for the demo to clean the DB on restart. !! NOT TO DO ON PROD !!

CREATE TABLE Subscriber
(
    id    int          NOT NULL,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)

);

INSERT INTO Subscriber
    (id, name, email)
VALUES (1, 'Marcus Ammar', 'marcus.ammar@gmail.com');