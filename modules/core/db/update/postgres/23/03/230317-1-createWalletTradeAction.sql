create table STARTUP_WALLET_TRADE_ACTION (
    ID bigint,
    UUID uuid,
    --
    PRICE double precision,
    ASSET double precision,
    MONEY double precision,
    ACTION_ varchar(50),
    WALLET_ID uuid not null,
    TIME_ timestamp,
    --
    primary key (ID)
);