package com.company.startup.examples.userdata;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExtendIsolatedMarginListenKey {
    private ExtendIsolatedMarginListenKey() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ExtendIsolatedMarginListenKey.class);
    public static void main(String[] args) {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        parameters.put("listenKey", "");

        String result = client.createUserData().extendIsolatedMarginListenKey(parameters);
        logger.info(result);
    }
}
