package com.company.startup.core.service;

import com.company.startup.core.db.WalletTradeActionDb;
import com.company.startup.model.constants.TradeAction;
import com.company.startup.model.constants.TradePair;
import com.company.startup.model.entity.PriceHistory;
import com.company.startup.model.entity.Wallet;
import com.company.startup.model.entity.WalletTradeAction;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

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
    private PriceService priceService;
    @Inject
    private AppService appService;

    private static final BigDecimal FEE = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(0.001));
    private static final BigDecimal INCREASE = BigDecimal.valueOf(1.005);
    private static final BigDecimal DECREASE = BigDecimal.valueOf(0.97);

    @Transactional
    @Override
    public void startSession() {


        Wallet wallet = walletService.getWalletByID(UUID.fromString(WalletService.WALLET_ID));

        double initBalance = 100.0;
        double aimBalance = initBalance * 1.10;
        double totalProfit = 0.0;
        double sessionBalance = 0.0;

        wallet.setMoney(100.0);
        wallet.setAsset(0.0);

        double firstBuy = wallet.getMoney() * 0.6;
        double nextBuy = (wallet.getMoney() - firstBuy) * 0.25;

        List<PriceHistory> priceHistories = priceService.getPriceList();

        boolean initialBuy = false;
        dataManager.commit(wallet);
        for (int i = 0; i < priceHistories.size(); i++) {
            wallet = dataManager.reload(wallet, "_local");
            Double currentPrice = priceHistories.get(i).getPrice();

            if (i == 0 || initialBuy) {
                buyWithAmount(wallet, currentPrice, firstBuy);
                initialBuy = false;
            } else {
                wallet = dataManager.reload(wallet, "_local");
                double balance = wallet.getMoney() + wallet.getAsset() * currentPrice;

                if (balance >= aimBalance) {
                    sellAsset(wallet, currentPrice);
                    double profit = balance - initBalance;
                    totalProfit = totalProfit + profit;
                    wallet = dataManager.reload(wallet, "_local");
                    wallet.setMoney(initBalance);
                    dataManager.commit(wallet);
                    initialBuy = true;

                } else {
                    if (currentPrice <= wallet.getPriceToAction() && wallet.getMoney() > nextBuy)
                        buyWithAmount(wallet, currentPrice, nextBuy);
                }
            }
        }

        double money = sessionBalance + totalProfit;

        System.out.println(totalProfit);
    }

    @Override
    public void initAction() {
        Wallet wallet = walletService.getWalletByID(UUID.fromString(WalletService.WALLET_ID));

        Double currentPrice = appService.getPrise(TradePair.NEARUSDT);

        try (Transaction tx = persistence.createTransaction()) {
            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setAction(wallet.getActionStatus().equals(TradeAction.BUY) ? TradeAction.SELL : TradeAction.BUY);
            walletAction.setCurrentPrice(currentPrice);
            persistence.getEntityManager().persist(walletAction);

            wallet.setPriceToAction(currentPrice);
            persistence.getEntityManager().merge(wallet);

            tx.commit();
        }
    }


    public void sell(Wallet wallet, Double currentPrice) {
        try (Transaction tx = persistence.createTransaction()) {

            double money = BigDecimal.valueOf(wallet.getAsset())
                    .multiply(BigDecimal.valueOf(currentPrice))
                    .multiply(FEE)
                    .setScale(3, RoundingMode.FLOOR).doubleValue();
            double asset = BigDecimal.ZERO.doubleValue();


            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.SELL);
            walletAction.setCurrentPrice(currentPrice);
            walletAction.setMoney(money);
            walletAction.setAsset(asset);
            persistence.getEntityManager().persist(walletAction);

            wallet.setPriceToAction(calculatePriceToAction(currentPrice, TradeAction.BUY));
            wallet.setMoney(money);
            wallet.setAsset(asset);
            wallet.setActionStatus(TradeAction.BUY);

            persistence.getEntityManager().merge(wallet);

            tx.commit();

        }
    }

    public void sellAsset(Wallet wallet, Double currentPrice) {
        try (Transaction tx = persistence.createTransaction()) {

            double money = wallet.getMoney() + BigDecimal.valueOf(wallet.getAsset())
                    .multiply(BigDecimal.valueOf(currentPrice))
                    .multiply(FEE)
                    .setScale(3, RoundingMode.FLOOR).doubleValue();
            double asset = BigDecimal.ZERO.doubleValue();


            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.SELL);
            walletAction.setCurrentPrice(currentPrice);
            walletAction.setMoney(money);
            walletAction.setAsset(asset);
            persistence.getEntityManager().persist(walletAction);

            wallet.setMoney(money);
            wallet.setAsset(asset);

            persistence.getEntityManager().merge(wallet);

            tx.commit();
        }
    }

    public void buyWithAmount(Wallet wallet, Double currentPrice, Double amount) {
        try (Transaction tx = persistence.createTransaction()) {

            double money = wallet.getMoney() - amount;
            double asset = wallet.getAsset() + BigDecimal.valueOf(amount)
                    .multiply(FEE)
                    .divide(BigDecimal.valueOf(currentPrice), 3, RoundingMode.FLOOR).doubleValue();

            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.BUY);
            walletAction.setCurrentPrice(currentPrice);
            walletAction.setMoney(money);
            walletAction.setAsset(asset);
            persistence.getEntityManager().persist(walletAction);

            wallet.setPriceToAction(currentPrice * 0.85);
            wallet.setMoney(money);
            wallet.setAsset(asset);

            persistence.getEntityManager().merge(wallet);

            tx.commit();

        }
    }

    public void buy(Wallet wallet, Double currentPrice) {
        try (Transaction tx = persistence.createTransaction()) {

            double money = BigDecimal.ZERO.doubleValue();
            double asset = BigDecimal.valueOf(wallet.getMoney())
                    .multiply(FEE)
                    .divide(BigDecimal.valueOf(currentPrice), 3, RoundingMode.FLOOR).doubleValue();

            WalletTradeAction walletAction = walletTradeActionDb.create(wallet);
            walletAction.setWallet(wallet);
            walletAction.setAction(TradeAction.BUY);
            walletAction.setCurrentPrice(currentPrice);
            walletAction.setMoney(money);
            walletAction.setAsset(asset);
            persistence.getEntityManager().persist(walletAction);

            wallet.setPriceToAction(calculatePriceToAction(currentPrice, TradeAction.SELL));
            wallet.setMoney(money);
            wallet.setAsset(asset);
            wallet.setActionStatus(TradeAction.SELL);
            persistence.getEntityManager().merge(wallet);

            tx.commit();

        }
    }

    double calculatePriceToAction(double currentPrice, TradeAction action) {
        BigDecimal priceToAction;
        if (action.equals(TradeAction.SELL)) {
            priceToAction = new BigDecimal(currentPrice).multiply(new BigDecimal(String.valueOf(INCREASE)));
        } else {
            priceToAction = new BigDecimal(currentPrice).multiply(new BigDecimal(String.valueOf(DECREASE)));
        }
        return priceToAction.setScale(3, RoundingMode.FLOOR).doubleValue();
    }

    void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Error", e);
        }
    }
}