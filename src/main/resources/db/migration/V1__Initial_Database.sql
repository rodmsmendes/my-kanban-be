CREATE TABLE CARDS (
    ID          BIGINT        NOT NULL AUTO_INCREMENT,
    DESCRIPTION VARCHAR(1000) NOT NULL,
    _COLUMN     INT           NOT NULL,

    CONSTRAINT PK_CARDS PRIMARY KEY(ID)
);
