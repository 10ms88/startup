package com.company.startup.core.service;

import com.company.startup.model.constants.TradePair;
import com.company.startup.model.entity.PriceHistory;

import java.util.List;

public interface PriceService {
    String NAME = "PriceService";

    void collectPriceHistory(int fromDaysAgo, String interval, TradePair tradePair);

    List<PriceHistory> getPriceList();
}
