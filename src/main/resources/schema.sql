CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(255) NOT NULL CHECK (LENGTH(TRIM(name)) > 0),
    email VARCHAR(255) NOT NULL UNIQUE CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description  VARCHAR(1000) NOT NULL CHECK (LENGTH(TRIM(description)) > 0),
    requestor_id BIGINT        NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(255)  NOT NULL CHECK (LENGTH(TRIM(name)) > 0),
    description  VARCHAR(1000) NOT NULL CHECK (LENGTH(TRIM(name)) > 0),
    is_available BOOLEAN       NOT NULL,
    owner_id     BIGINT        NOT NULL REFERENCES users (id),
    request_id   BIGINT REFERENCES requests (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id    BIGINT                      NOT NULL REFERENCES items (id),
    booker_id  BIGINT                      NOT NULL REFERENCES users (id),
    status VARCHAR(10) NOT NULL CHECK ( status IN ('WAITING', 'APPROVED', 'REJECTED', 'CANCELED'))
);

CREATE TABLE IF NOT EXISTS comments
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(1000) NOT NULL CHECK (LENGTH(TRIM(text)) > 0),
    item_id    BIGINT                      NOT NULL REFERENCES items (id),
    author_id  BIGINT                      NOT NULL REFERENCES users (id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
