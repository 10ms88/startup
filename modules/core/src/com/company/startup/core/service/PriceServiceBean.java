package com.company.startup.core.service;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;


@Service(PriceService.NAME)
public class PriceServiceBean implements PriceService {


    @Override
    public String checkPrise(String symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();
        parameters.put("symbol", symbol);
        return client.createMarket().averagePrice(parameters);
    }
}
