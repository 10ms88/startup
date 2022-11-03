package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;
import com.company.startup.core.client.model.enums.CandlestickInterval;

public class SubscribeCandlestick {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
