-- begin STARTUP_WALLET_TRADE_ACTION
create table STARTUP_WALLET_TRADE_ACTION (
    ID bigint,
    UUID uuid,
    --
    WALLET_ID uuid not null,
    ACTION_ varchar(50),
    PRICE double precision,
    --
    primary key (ID)
)^
-- end STARTUP_WALLET_TRADE_ACTION
-- begin STARTUP_WALLET
create table STARTUP_WALLET (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    AMOUNT double precision,
    PRICE_TO_ACTION double precision,
    ASSET double precision,
    TRADE_PAIR varchar(50),
    ACTION_STATUS varchar(50),
    --
    primary key (ID)
)^
-- end STARTUP_WALLET
