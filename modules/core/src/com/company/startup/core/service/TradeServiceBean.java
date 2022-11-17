package com.company.startup.core.service;

import com.company.startup.core.db.WalletTradeActionDb;
import com.company.startup.model.constants.TradeAction;
import com.company.startup.model.constants.TradePair;
import com.company.startup.model.entity.Wallet;
import com.company.startup.model.entity.WalletTradeAction;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service(TradeService.NAME)
public class TradeServiceBean implements TradeService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TradeServiceBean.class);
    @Inject
    private WalletService walletService;
    @Inject
    private WalletTradeActionDb walletTradeActionDb;
    @Inject
    private Persistence persistence;
    @Inject
    private DataManager dataManager;

    @Inject
    private AppService appService;

    private static final BigDecimal FEE = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(0.001));
    private static final BigDecimal INCREASE = BigDecimal.valueOf(1.03);
    private static final BigDecimal DECREASE = BigDecimal.valueOf(0.97);

    @Override
    public void startSession() {

        Wallet wallet = walletService.getWalletByID(UUID.fromString("0e7b9103-8ce0-048a-4679-d203155661dc"));
//        Double currentPrice = appService.getPrise(TradePair.NEARUSDT);

        List<Double> list = new ArrayList<>();
        double currentPrice = 2.0;
        for (int i = 0; i < 1000; i++) {
            currentPrice = currentPrice * ThreadLocalRandom.current().nextDouble(DECREASE.doubleValue(), INCREASE.doubleValue());

            wallet = dataManager.reload(wallet, "_local");

            if (wallet.getActionStatus().equals(TradeAction.SELL) && currentPrice >= wallet.getPriceToAction()) {
                sell(wallet, currentPrice);
            } else if (wallet.getActionStatus().equals(TradeAction.BUY) && currentPrice <= wallet.getPriceToAction()) {
                buy(wallet, currentPrice);
            }
//

        }
        //       try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                log.error("Error", e);
        //      }
//
//
//        while (true) {
//            if (wallet.getActionStatus().equals(TradeAction.SELL) && currentPrice >= wallet.getPriceToAction()) {
//                sell(wallet, currentPrice);
//            } else if (wallet.getActionStatus().equals(TradeAction.BUY) && currentPrice <= wallet.getPriceToAction()) {
//                buy(wallet, currentPrice);
//            }
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                log.error("Error", e);
//            }
//        }


    }

    @Override
    public void initAction() {
        Wallet wallet = walletService.getWalletByID(UUID.fromString("0e7b9103-8ce0-048a-4679-d203155661dc"));

        Double currentPrice = appService.getPrise(TradePair.NEARUSDT);

        try (Transaction tx = persistence.createTransaction()) {
            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setAction(wallet.getActionStatus().equals(TradeAction.BUY) ? TradeAction.SELL : TradeAction.BUY);
            walletAction.setPrice(currentPrice);
            persistence.getEntityManager().persist(walletAction);

            wallet.setPriceToAction(getPriceToAction(currentPrice, wallet.getActionStatus()));
            persistence.getEntityManager().merge(wallet);

            tx.commit();
        }
    }


    public void sell(Wallet wallet, Double currentPrice) {
        try (Transaction tx = persistence.createTransaction()) {
            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.SELL);
            walletAction.setPrice(currentPrice);
            persistence.getEntityManager().persist(walletAction);

            BigDecimal amount = BigDecimal.valueOf(wallet.getAsset()).multiply(BigDecimal.valueOf(currentPrice)).multiply(FEE);

            wallet.setPriceToAction(getPriceToAction(currentPrice, TradeAction.SELL));
            wallet.setAmount(amount.setScale(3, RoundingMode.FLOOR).doubleValue());
            wallet.setAsset(BigDecimal.ZERO.doubleValue());
            wallet.setActionStatus(TradeAction.BUY);

            persistence.getEntityManager().merge(wallet);

            tx.commit();

        }
    }

    public void buy(Wallet wallet, Double currentPrice) {
        try (Transaction tx = persistence.createTransaction()) {
            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.BUY);
            walletAction.setPrice(currentPrice);
            persistence.getEntityManager().persist(walletAction);

            BigDecimal amount = BigDecimal.valueOf(wallet.getAmount())
                    .multiply(FEE)
                    .divide(BigDecimal.valueOf(currentPrice), 3, RoundingMode.FLOOR);

            wallet.setPriceToAction(getPriceToAction(currentPrice, TradeAction.BUY));
            wallet.setAmount(amount.doubleValue());
            wallet.setAsset(BigDecimal.ZERO.doubleValue());
            wallet.setActionStatus(TradeAction.SELL);
            persistence.getEntityManager().merge(wallet);

            tx.commit();

        }
    }

    double getPriceToAction(double currentPrice, TradeAction action) {
        BigDecimal priceToAction;
        if (action.equals(TradeAction.SELL)) {
            priceToAction = new BigDecimal(currentPrice).multiply(new BigDecimal(String.valueOf(INCREASE)));
        } else {
            priceToAction = new BigDecimal(currentPrice).multiply(new BigDecimal(String.valueOf(DECREASE)));
        }
        return priceToAction.setScale(3, RoundingMode.FLOOR).doubleValue();
    }
}