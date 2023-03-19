package com.company.startup.examples.market;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;

import com.company.startup.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Klines {

    /**
     * [
     *   [
     *     1499040000000,      // Kline open time
     *     "0.01634790",       // Open price
     *     "0.80000000",       // High price
     *     "0.01575800",       // Low price
     *     "0.01577100",       // Close price
     *     "148976.11427815",  // Volume
     *     1499644799999,      // Kline Close time
     *     "2434.19055334",    // Quote asset volume
     *     308,                // Number of trades
     *     "1756.87402397",    // Taker buy base asset volume
     *     "28.46694368",      // Taker buy quote asset volume
     *     "0"                 // Unused field, ignore.
     *   ]
     * ]
     */
    private Klines() {
    }

    private static final Logger logger = LoggerFactory.getLogger(Klines.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        parameters.put("symbol", "NEARUSDT");
        parameters.put("interval", "1m");
        parameters.put("limit", "1000");

        long ts = System.currentTimeMillis()/1000;
        parameters.put("startTime", 1675209600000L);

        String result = client.createMarket().klines(parameters);
        logger.info(result);
    }
}