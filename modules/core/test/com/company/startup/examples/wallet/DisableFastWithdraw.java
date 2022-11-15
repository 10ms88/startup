package com.company.startup.examples.wallet;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DisableFastWithdraw {
    private DisableFastWithdraw() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DisableFastWithdraw.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createWallet().disableFastWithdraw(parameters);
        logger.info(result);
    }
}
