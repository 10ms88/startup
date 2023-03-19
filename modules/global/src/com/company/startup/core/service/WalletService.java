package com.company.startup.core.service;

import com.company.startup.model.entity.Wallet;

import java.util.UUID;

public interface WalletService {
    String NAME = "st_WalletService";
    String WALLET_ID = "fffe6167-b776-cac1-9170-765c9775d98a";

    void create();


    Wallet getWalletByID(UUID uuid);

}