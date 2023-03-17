package com.company.startup.core.service;

import com.company.startup.model.constants.Symbol;
import com.company.startup.model.constants.TradePair;

public interface AppService {

    String NAME = "st_AppService";

    String getPriseInfo(String symbol);

    String getAssetInfo(String asset);


    Double getPrise(TradePair tradePair);

    Double getAsset(Symbol asset);

}

