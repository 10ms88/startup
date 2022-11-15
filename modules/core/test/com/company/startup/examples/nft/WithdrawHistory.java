package com.company.startup.examples.nft;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public final class WithdrawHistory {
    private WithdrawHistory() {
    }

    private static final Logger logger = LoggerFactory.getLogger(WithdrawHistory.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createNFT().withdrawHistory(parameters);
        logger.info(result);
    }
}
