package com.company.startup.core.service;

import com.company.startup.core.db.WalletTradeActionDb;
import com.company.startup.model.constants.Symbol;
import com.company.startup.model.constants.TradeAction;
import com.company.startup.model.constants.TradePair;
import com.company.startup.model.entity.Wallet;
import com.company.startup.model.entity.WalletTradeAction;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(WalletService.NAME)
public class WalletServiceBean implements WalletService {


    @Inject
    private Persistence persistence;

    @Inject
    private DataManager dataManager;

    @Inject
    private AppService appService;



    @Override
    public void create() {
        JSONArray assets = new JSONArray(appService.getAssetInfo(null));
        Double assetUSDT = getAsset(assets, Symbol.USDT);
        Double assetNEAR = getAsset(assets, Symbol.NEAR);
        TradeAction action = Double.compare(assetUSDT, assetNEAR) < 0 ? TradeAction.SELL : TradeAction.BUY;
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            Wallet wallet = dataManager.create(Wallet.class);
            wallet.setActionStatus(action);
            wallet.setTradePair(TradePair.NEARUSDT);
            wallet.setAsset(assetNEAR);
            wallet.setAmount(assetUSDT);
            entityManager.persist(wallet);
            tx.commit();
        }
    }


    @Override
    public Wallet getWalletByID(UUID uuid) {
        LoadContext<Wallet> loadContext = LoadContext.create(Wallet.class)
                .setQuery(LoadContext.createQuery(
                        "select w from startup_Wallet w where w.id= :id")
                        .setParameter("id", uuid));

        return dataManager.load(loadContext);
    }

    public Double getAsset(JSONArray assets, Symbol asset) {
        Double result = null;
        for (int i = 0; i < assets.length(); i++) {
            JSONObject jsonObject = assets.getJSONObject(i);
            if (jsonObject.get("asset").equals(asset.getId())) {
                result = Double.parseDouble((String) jsonObject.get("free"));
                break;
            }
        }
        return result;
    }
}