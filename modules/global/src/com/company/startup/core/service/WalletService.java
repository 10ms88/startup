package com.company.startup.core.service;

import com.company.startup.model.entity.Wallet;

import java.util.UUID;

public interface WalletService {
    String NAME = "st_WalletService";
    String WALLET_ID = "6666965c-c767-c341-d878-48761e188e9d";

    void create();


    Wallet getWalletByID(UUID uuid);

}