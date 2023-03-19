package com.company.startup;

import com.company.startup.core.service.PriceService;
import com.company.startup.core.service.TradeService;
import com.company.startup.model.constants.TradePair;
import com.haulmont.cuba.core.global.AppBeans;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TestTest {

    @ClassRule
    public static StartupTestContainer cont = StartupTestContainer.Common.INSTANCE;


    private PriceService priceService;
    private TradeService tradeService;
    @Before
    public void setUp()  {
        priceService = AppBeans.get(PriceService.class);
        tradeService = AppBeans.get(TradeService.class);

    }


    @Test
    public void startTest() {
        tradeService.startSession();
//        priceService.collectPriceHistory(30,"5m", TradePair.NEARUSDT);

    }

}
