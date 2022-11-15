package com.company.startup.examples.margin;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AllAssets {
    private AllAssets() {
    }

    private static final Logger logger = LoggerFactory.getLogger(AllAssets.class);
    public static void main(String[] args) {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createMargin().allAssets();
        logger.info(result);
    }
}
