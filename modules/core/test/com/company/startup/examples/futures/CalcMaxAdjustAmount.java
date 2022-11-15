package com.company.startup.examples.futures;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CalcMaxAdjustAmount {
    private CalcMaxAdjustAmount() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CalcMaxAdjustAmount.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("loanCoin", "USDT");
        parameters.put("collateralCoin", "BUSD");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createFutures().calcMaxAdjustAmount(parameters);
        logger.info(result);
    }
}
