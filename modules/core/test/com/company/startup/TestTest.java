package com.company.startup;

import com.company.startup.core.db.WalletTradeActionDb;
import com.company.startup.core.service.TradeService;
import com.company.startup.core.service.WalletService;
import com.haulmont.cuba.core.global.AppBeans;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TestTest {

    @ClassRule
    public static StartupTestContainer cont = StartupTestContainer.Common.INSTANCE;

    private WalletService walletService;
    private WalletTradeActionDb walletTradeActionDb;

    private TradeService tradeService;

    @Before
    public void setUp() throws Exception {
        walletService = AppBeans.get(WalletService.class);
        walletTradeActionDb = AppBeans.get(WalletTradeActionDb.class);
        tradeService = AppBeans.get(TradeService.class);

    }


    @Test
    public void startTest() {
//        tradeService.initAction();
        walletService.create();
        tradeService.startSession();


    }

}
