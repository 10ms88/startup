package com.company.startup.examples.futures;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CollateralRepay {
    private CollateralRepay() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CollateralRepay.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("quoteId", "test");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createFutures().collateralRepay(parameters);
        logger.info(result);
    }
}
