create table STARTUP_WALLET_TRADE_ACTION (
    ID bigint,
    UUID uuid,
    --
    WALLET_ID uuid not null,
    ACTION_ varchar(50),
    PRICE decimal(19, 2),
    --
    primary key (ID)
);