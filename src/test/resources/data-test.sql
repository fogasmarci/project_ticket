DROP
ALL OBJECTS;

CREATE TABLE PRODUCT_TYPES
(
    ID   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(100) DEFAULT NULL
);

INSERT INTO PRODUCT_TYPES (NAME)
VALUES ('jegy'),
       ('bérlet');

CREATE TABLE PRODUCTS
(
    ID          int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME        varchar(100) DEFAULT NULL,
    PRICE       int          DEFAULT NULL,
    DURATION    int          DEFAULT NULL,
    DESCRIPTION varchar(100) DEFAULT NULL,
    TYPE_ID     int          DEFAULT NULL,
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
    ID          INT NOT NULL AUTO_INCREMENT,
    TITLE       VARCHAR(100) DEFAULT NULL,
    CONTENT     VARCHAR(100) DEFAULT NULL,
    PUBLISHDATE DATE         DEFAULT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO news (TITLE, CONTENT, PUBLISHDATE)
VALUES ('News about tickets', 'Ipsum Lorum', '2023-12-11');
INSERT INTO news (TITLE, CONTENT, PUBLISHDATE)
VALUES ('Test Title', 'Test Content', '2023-12-11');

CREATE TABLE CARTS
(
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY
);

INSERT INTO CARTS (ID)
VALUES (default),
       (default);

CREATE TABLE CART_PRODUCT
(
    CART_ID    int NOT NULL,
    PRODUCT_ID int NOT NULL
);

CREATE TABLE PRODUCTS_CARTS
(
    CARTS_ID    int NOT NULL,
    PRODUCTS_ID int NOT NULL
);

CREATE TABLE USERS
(
    ID         int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME       varchar(100) DEFAULT NULL,
    PASSWORD   varchar(100) DEFAULT NULL,
    EMAIL      varchar(100) DEFAULT NULL UNIQUE,
    ROLES      varchar(100) DEFAULT NULL,
    ISADMIN    BOOLEAN      DEFAULT false,
    ISVERIFIED BOOLEAN      DEFAULT false,
    CART_ID    int NOT NULL,
    CONSTRAINT FK_CART
        FOREIGN KEY (CART_ID)
            REFERENCES CARTS (ID)
);

INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLES, CART_ID, ISADMIN, ISVERIFIED)
VALUES ('TestUser', 'user@user.user', '$2a$10$n.AMx5SrMrlOnJSmsTrgU.rvT4GFKsBFFaGJ8W3JjB8JNcroGx5ga', 'ROLE_USER',
        (SELECT ID FROM CARTS LIMIT 1), false, false );

INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLES, CART_ID, ISADMIN, ISVERIFIED)
VALUES ('TestAdmin', 'admin@admin.admin', '$2a$10$YpqEwYeCugXTRDRtC1RnAuRsBIDgSzot30jRp1B5HZzn6j5drpJeO',
        'ROLE_USER,ROLE_ADMIN', (SELECT ID FROM CARTS LIMIT 1 OFFSET 1), true, true );
