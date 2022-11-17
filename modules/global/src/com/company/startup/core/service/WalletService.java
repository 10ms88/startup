package com.company.startup.core.service;

import com.company.startup.model.entity.Wallet;

import java.util.UUID;

public interface WalletService {
    String NAME = "st_WalletService";


    void create();


    Wallet getWalletByID (UUID uuid);

}