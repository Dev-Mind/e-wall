CREATE TABLE AUTHORITY
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NAME VARCHAR(255) NOT NULL
);
CREATE TABLE CATEGORY
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    CREATEDAT TIMESTAMP,
    NAME VARCHAR(255)
);
CREATE TABLE QRCODE
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    GENERATEDAT TIMESTAMP,
    CATEGORY_ID BIGINT NOT NULL,
    WORK_ID BIGINT
);
CREATE TABLE USER
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    EMAIL VARCHAR(255),
    ESMEID VARCHAR(255),
    FIRSTCONNECTIONAT TIMESTAMP,
    FIRSTCONNEXION TIMESTAMP,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255),
    TOKEN VARCHAR(255),
    TOKENEXPIRATION TIMESTAMP
);
CREATE TABLE USER_AUTHORITY
(
    USER_ID BIGINT NOT NULL,
    AUTHORITIES_ID BIGINT NOT NULL,
    PRIMARY KEY (USER_ID, AUTHORITIES_ID)
);
CREATE TABLE WORK
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    CONTENT CLOB,
    CREATEDAT TIMESTAMP,
    USER_ID BIGINT NOT NULL
);
ALTER TABLE QRCODE ADD FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID);
ALTER TABLE QRCODE ADD FOREIGN KEY (WORK_ID) REFERENCES WORK (ID);
CREATE INDEX FK_3YONWMFK6VPSHB0N2CC0TOYFH_INDEX_8 ON QRCODE (WORK_ID);
CREATE INDEX FK_LRXL47CEVKKUIG3KYMJD791KR_INDEX_8 ON QRCODE (CATEGORY_ID);
ALTER TABLE USER_AUTHORITY ADD FOREIGN KEY (AUTHORITIES_ID) REFERENCES AUTHORITY (ID);
ALTER TABLE USER_AUTHORITY ADD FOREIGN KEY (USER_ID) REFERENCES USER (ID);
CREATE INDEX FK_S5AERDIQYT5E9QREWACJS4MSS_INDEX_B ON USER_AUTHORITY (AUTHORITIES_ID);
ALTER TABLE WORK ADD FOREIGN KEY (USER_ID) REFERENCES USER (ID);
CREATE INDEX FK_FFEVXL8SDHYH17NE0BOSP6XE8_INDEX_2 ON WORK (USER_ID);
