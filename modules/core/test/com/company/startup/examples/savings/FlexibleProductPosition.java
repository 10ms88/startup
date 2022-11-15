package com.company.startup.examples.savings;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FlexibleProductPosition {
    private FlexibleProductPosition() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FlexibleProductPosition.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createSavings().flexibleProductPosition(parameters);
        logger.info(result);
    }
}
