package com.company.startup.examples.margin;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public final class CrossMarginData {
    private CrossMarginData() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CrossMarginData.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("coin", "BNB");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createMargin().crossMarginData(parameters);
        logger.info(result);
    }
}
