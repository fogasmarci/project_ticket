UPDATE flyway_schema_history
SET checksum = '<new_checksum>'
WHERE version = '0.0.1';

CREATE TABLE PRODUCT_TYPES
(
    ID   bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(100) DEFAULT NULL
);

INSERT INTO PRODUCT_TYPES (NAME)
VALUES ('jegy'),
       ('bérlet');

CREATE TABLE PRODUCTS
(
    ID          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME        varchar(100) DEFAULT NULL,
    PRICE       integer      DEFAULT NULL,
    DURATION    integer      DEFAULT NULL,
    DESCRIPTION varchar(100) DEFAULT NULL,
    TYPE_ID     bigint       DEFAULT NULL,
    CONSTRAINT FK_TYPE
        FOREIGN KEY (TYPE_ID)
            REFERENCES PRODUCT_TYPES (ID)
);

INSERT INTO PRODUCTS (NAME, PRICE, DURATION, DESCRIPTION, TYPE_ID)
VALUES ('teszt jegy 1', 480, 90, 'teszt1', 1),
       ('teszt bérlet 1', 4000, 9000, 'teszt2', 2),
       ('teszt bérlet 2', 9500, 9000, 'teszt3', 2);

CREATE TABLE news
(
    ID           bigint NOT NULL AUTO_INCREMENT,
    TITLE        VARCHAR(100) DEFAULT NULL,
    CONTENT      VARCHAR(100) DEFAULT NULL,
    PUBLISH_DATE DATE         DEFAULT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO news (TITLE, CONTENT, PUBLISH_DATE)
VALUES ('News about tickets', 'Ipsum Lorum', '2023-12-11');
INSERT INTO news (TITLE, CONTENT, PUBLISH_DATE)
VALUES ('Test Title', 'Test Content', '2023-12-11');

CREATE TABLE CARTS
(
    ID bigint NOT NULL AUTO_INCREMENT PRIMARY KEY
);

INSERT INTO CARTS (ID)
VALUES (default),
       (default),
       (default);

CREATE TABLE CART_PRODUCT
(
    CART_ID    bigint NOT NULL,
    PRODUCT_ID bigint NOT NULL
);

INSERT INTO CART_PRODUCT(CART_ID, PRODUCT_ID)
VALUES ((SELECT ID FROM CARTS LIMIT 1 OFFSET 2), 2),
       ((SELECT ID FROM CARTS LIMIT 1 OFFSET 2), 2),
       ((SELECT ID FROM CARTS LIMIT 1 OFFSET 2), 1);

CREATE TABLE PRODUCTS_CARTS
(
    CARTS_ID   bigint NOT NULL,
    PRODUCT_ID bigint NOT NULL
);

CREATE TABLE USERS
(
    ID          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME        varchar(100) DEFAULT NULL,
    PASSWORD    varchar(100) DEFAULT NULL,
    EMAIL       varchar(100) DEFAULT NULL UNIQUE,
    ROLES       varchar(100) DEFAULT NULL,
    IS_ADMIN    BOOLEAN      DEFAULT false,
    IS_VERIFIED BOOLEAN      DEFAULT false,
    CART_ID     bigint NOT NULL,
    CONSTRAINT FK_CART
        FOREIGN KEY (CART_ID)
            REFERENCES CARTS (ID)
);

INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLES, IS_ADMIN, IS_VERIFIED, CART_ID)
VALUES ('Admin', 'admin@admin.com', '$2a$10$x44csP50u/GqqeNtLW/44OLrzGq0taFv7nIb86aUw2gvrEJqiR8By',
        'ROLE_USER,ROLE_ADMIN', TRUE, TRUE, (SELECT ID FROM CARTS LIMIT 1) );
