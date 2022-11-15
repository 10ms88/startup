package com.company.startup.examples.bswap;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SwapPools {
    private SwapPools() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SwapPools.class);
    public static void main(String[] args) {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createBswap().swapPools();
        logger.info(result);
    }
}
