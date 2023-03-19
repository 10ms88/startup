create table PRICE_HISTORY (
    ID bigint,
    UUID uuid,
    --
    TS bigint,
    SYMBOL varchar(255),
    PRICE double precision,
    DATE_ date,
    --
    primary key (ID)
);