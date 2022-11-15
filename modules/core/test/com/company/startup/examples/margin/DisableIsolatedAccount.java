package com.company.startup.examples.margin;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DisableIsolatedAccount {
    private DisableIsolatedAccount() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DisableIsolatedAccount.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        parameters.put("symbol", "BNBUSDT");

        String result = client.createMargin().disableIsolatedAccount(parameters);
        logger.info(result);
    }
}
