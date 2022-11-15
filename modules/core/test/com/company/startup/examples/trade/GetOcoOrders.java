package com.company.startup.examples.trade;

import com.company.startup.core.connector.client.exceptions.BinanceClientException;
import com.company.startup.core.connector.client.exceptions.BinanceConnectorException;
import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GetOcoOrders {
    private GetOcoOrders() {
    }
    private static final int fromId = 1;
    private static final int limit = 10;

    private static final Logger logger = LoggerFactory.getLogger(GetOcoOrders.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.BASE_URL);

        parameters.put("fromId", fromId);
        parameters.put("limit", limit);

        try {
            String result = client.createTrade().getOCOOrders(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
    }
}
