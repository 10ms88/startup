package com.company.startup.examples.subaccount;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SubAccountDepositHistory {
    private SubAccountDepositHistory() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SubAccountDepositHistory.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("email", "");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createSubAccount().depositHistory(parameters);
        logger.info(result);
    }
}
