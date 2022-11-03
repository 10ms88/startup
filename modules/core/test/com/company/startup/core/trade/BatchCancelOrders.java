package com.company.startup.core.trade;

import com.alibaba.fastjson.JSONArray;
import com.company.startup.core.client.RequestOptions;
import com.company.startup.core.client.SyncRequestClient;
import com.company.startup.core.constants.PrivateConfig;


/**
 * @author : wangwanlu
 * @since : 2020/4/7, Tue
 **/
public class BatchCancelOrders {

    public static void main(String[] args) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // batch cancel by order ids
        JSONArray orderIds = new JSONArray();
        orderIds.add(180L);
        orderIds.add(181L);
        System.out.println(syncRequestClient.batchCancelOrders("BTCUSDT", orderIds.toJSONString(), null));

        // batch cancel by client order ids
//        JSONArray origClientOrderIds = new JSONArray();
//        origClientOrderIds.add("cli_order_001");
//        origClientOrderIds.add("cli_order_002");
//        System.out.println(syncRequestClient.batchCancelOrders("BTCUSDT", null, origClientOrderIds.toJSONString()));
    }
}
