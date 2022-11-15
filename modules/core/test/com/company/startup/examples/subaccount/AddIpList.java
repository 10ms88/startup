package com.company.startup.examples.subaccount;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public final class AddIpList {
    private AddIpList() {
    }

    private static final Logger logger = LoggerFactory.getLogger(AddIpList.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("email", "");
        parameters.put("subAccountApiKey", "");
        parameters.put("ipAddress", "");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createSubAccount().addIpList(parameters);
        logger.info(result);
    }
}
