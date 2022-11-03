package com.company.startup.core.user;

import com.company.startup.core.client.RequestOptions;
import com.company.startup.core.client.SubscriptionClient;
import com.company.startup.core.client.SyncRequestClient;
import com.company.startup.core.constants.PrivateConfig;

public class SubscribeUserData {

    public static void main(String[] args) {

        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // Start user data stream
        String listenKey = syncRequestClient.startUserDataStream();
        System.out.println("listenKey: " + listenKey);

        // Keep user data stream
        syncRequestClient.keepUserDataStream(listenKey);

        // Close user data stream
        syncRequestClient.closeUserDataStream(listenKey);

        SubscriptionClient client = SubscriptionClient.create();

   
        client.subscribeUserDataEvent(listenKey, System.out::println, null);

    }

}