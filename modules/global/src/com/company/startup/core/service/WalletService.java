package com.company.startup.core.service;

import com.company.startup.model.entity.Wallet;

import java.util.UUID;

public interface WalletService {
    String NAME = "st_WalletService";
    String WALLET_ID = "ebac358a-9881-1a99-a5e3-2595ff46ec8a";

    void create();


    Wallet getWalletByID(UUID uuid);

}