package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;

public class SubscribeBookDepth {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeBookDepthEvent("btcusdt", 5, ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
