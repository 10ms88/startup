package com.company.startup.core.market;

import com.company.startup.core.client.RequestOptions;
import com.company.startup.core.client.SyncRequestClient;
import com.company.startup.core.constants.PrivateConfig;

public class GetSymbolPriceTicker {
    public static void main(String[] args) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getSymbolPriceTicker("BTCUSDT"));
        // System.out.println(syncRequestClient.getSymbolPriceTicker(null));
    }
}
