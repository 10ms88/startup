package com.company.startup;

import com.company.startup.core.service.PriceService;
import com.company.startup.core.service.WbService;
import com.haulmont.cuba.core.global.AppBeans;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TestTest {

    @ClassRule
    public static StartupTestContainer cont = StartupTestContainer.Common.INSTANCE;


    private PriceService priceService;
    private WbService wbService;

    @Before
    public void setUp() {
        wbService = AppBeans.get(WbService.class);


    }


    @Test
    public void startTest() {
        wbService.getOrders();
    }

}
