package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;

public class SubscribeAllBookTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeAllBookTickerEvent(System.out::println, null);

    }

}
