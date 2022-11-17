package com.company.startup.core.service;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.model.constants.PrivateConfig;
import com.company.startup.model.constants.Symbol;
import com.company.startup.model.constants.TradePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.stream.Stream;


@Service(AppService.NAME)
public class AppServiceBean implements AppService {

    private SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

    @Override
    public String getPriseInfo(String symbol) {
        return sendPriceRequest(symbol);
    }

    @Override
    public String getAssetInfo(String asset) {
        return sendAssetRequest(asset);
    }

    @Override
    public Double getPrise(TradePair tradePair) {
        return Double.parseDouble((String) new JSONObject(sendPriceRequest(tradePair.getId())).get("price"));
    }


    @Override
    public Double getAsset(Symbol asset) {
        String result = sendAssetRequest(null);
        return Double.parseDouble((String) Stream.of(new JSONArray(result))
                .filter(l -> l.getJSONObject(0).get("asset").equals(asset.getId()))
                .findFirst().get().getJSONObject(0).get("free"));

    }


    private String sendPriceRequest(String symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();
        parameters.put("symbol", symbol);
        return client.createMarket().averagePrice(parameters);

    }

    private String sendAssetRequest(String asset) {

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (asset != null) {
            parameters.put("asset", asset);
        }
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        return client.createWallet().getUserAsset(parameters);

    }
}
