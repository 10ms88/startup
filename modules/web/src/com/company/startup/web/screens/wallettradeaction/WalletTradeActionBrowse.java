package com.company.startup.web.screens.wallettradeaction;

import com.haulmont.cuba.gui.screen.*;
import com.company.startup.model.entity.WalletTradeAction;

@UiController("startup_WalletTradeAction.browse")
@UiDescriptor("wallet-trade-action-browse.xml")
@LookupComponent("walletTradeActionsTable")
@LoadDataBeforeShow
public class WalletTradeActionBrowse extends StandardLookup<WalletTradeAction> {
}