package com.company.startup.examples.rebate;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public final class TaxQuery {
    private TaxQuery() {
    }

    private static final Logger logger = LoggerFactory.getLogger(TaxQuery.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createRebate().taxQuery(parameters);
        logger.info(result);
    }
}
