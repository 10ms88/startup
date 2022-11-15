package com.company.startup.examples.market;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public final class UIKlines {
    private UIKlines() {
    }

    private static final Logger logger = LoggerFactory.getLogger(Klines.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        parameters.put("symbol", "BNBUSDT");
        parameters.put("interval", "1m");

        String result = client.createMarket().uiKlines(parameters);
        logger.info(result);
    }
}
