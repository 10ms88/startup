-- begin STARTUP_WALLET_TRADE_ACTION
create table STARTUP_WALLET_TRADE_ACTION (
    ID bigint,
    UUID uuid,
    --
    PRICE double precision,
    ASSET double precision,
    MONEY double precision,
    ACTION_ varchar(50),
    WALLET_ID uuid not null,
    TIME_ timestamp with time zone,
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
    MONEY double precision,
    PRICE_TO_ACTION double precision,
    ASSET double precision,
    TRADE_PAIR varchar(50),
    ACTION_STATUS varchar(50),
    --
    primary key (ID)
)^
-- end STARTUP_WALLET
