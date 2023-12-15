DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    ID       int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME     varchar(100) DEFAULT NULL,
    PASSWORD varchar(100) DEFAULT NULL,
    EMAIL    varchar(100) DEFAULT NULL UNIQUE,
    ROLES    varchar(100) DEFAULT NULL
);

INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLES)
VALUES ('TestUser', 'user@user.user', '12345678', 'ROLE_USER');
INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLES)
VALUES ('TestAdmin', 'admin@admin.admin', 'adminadmin', 'ROLE_USER,ROLE_ADMIN');

DROP TABLE IF EXISTS PRODUCTS;

DROP TABLE IF EXISTS PRODUCT_TYPES;
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