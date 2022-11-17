package com.company.startup.core.service;

import com.company.startup.model.entity.Wallet;

public interface TradeService {
    String NAME = "startup_TradeService";

    void startSession();

    void initAction();

}