package com.company.startup.examples.futures;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoanConfigs {
    private LoanConfigs() {
    }

    private static final Logger logger = LoggerFactory.getLogger(LoanConfigs.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createFutures().loanConfigs(parameters);
        logger.info(result);
    }
}
