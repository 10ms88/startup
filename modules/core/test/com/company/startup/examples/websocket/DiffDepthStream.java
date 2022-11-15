package com.company.startup.examples.websocket;

import com.company.startup.core.connector.client.impl.WebsocketClientImpl;

public final class DiffDepthStream {
    private DiffDepthStream() {
    }
    private static final int speed = 100;

    public static void main(String[] args) {
        WebsocketClientImpl client = new WebsocketClientImpl();
        client.diffDepthStream("btcusdt", speed, ((event) -> {
            System.out.println(event);
            client.closeAllConnections();
        }));
    }
}
