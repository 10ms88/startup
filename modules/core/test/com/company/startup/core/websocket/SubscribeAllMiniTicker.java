package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;

public class SubscribeAllMiniTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeAllMiniTickerEvent(System.out::println, null);

    }

}
