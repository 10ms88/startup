package com.company.startup.core.websocket;

import com.company.startup.core.client.SubscriptionClient;

public class SubscribeAllLiquidationOrder {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeAllLiquidationOrderEvent(System.out::println, null);

    }

}
