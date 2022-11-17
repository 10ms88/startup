alter table STARTUP_WALLET_TRADE_ACTION rename column time_ to time___u44147 ;
alter table STARTUP_WALLET_TRADE_ACTION add column TIME_ timestamp with time zone ;
