package com.company.startup.core.db;

import com.company.startup.model.entity.Wallet;
import com.company.startup.model.entity.WalletTradeAction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class WalletTradeActionDb {

    @Inject
    private DataManager dataManager;

    public WalletTradeAction create(Wallet wallet) {
        WalletTradeAction action = dataManager.create(WalletTradeAction.class);
        action.setWallet(wallet);
        return action;
    }


    public List<WalletTradeAction> getActionsByWallet(Wallet wallet) {

        LoadContext<WalletTradeAction> loadContext = LoadContext.create(WalletTradeAction.class)
                .setQuery(LoadContext.createQuery(
                        "select w from startup_WalletTradeAction w where w.wallet= :wallet")
                        .setParameter("wallet", wallet))
                .setView("walletTradeAction-view");


        return dataManager.loadList(loadContext);


    }
}
