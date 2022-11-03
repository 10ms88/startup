package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;

public class SubscribeDiffDepth {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeDiffDepthEvent("btcusdt", ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
