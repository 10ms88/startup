package com.company.startup.core.trade;

import com.company.startup.core.client.RequestOptions;
import com.company.startup.core.client.SyncRequestClient;
import com.company.startup.core.constants.PrivateConfig;

/**
 * @author : wangwanlu
 * @since : 2020/4/7, Tue
 **/
public class GetPositionSide {

    public static void main(String[] args) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getPositionSide());
    }
}
