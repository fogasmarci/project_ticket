CREATE TABLE `USERS`
(
    `ID`       int NOT NULL AUTO_INCREMENT,
    `NAME`     varchar(100) DEFAULT NULL,
    `EMAIL`    varchar(100) DEFAULT NULL,
    `PASSWORD` varchar(100) DEFAULT NULL,
    `ROLES`    varchar(100) DEFAULT NULL,
    PRIMARY KEY (`ID`)
);