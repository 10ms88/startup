package com.company.startup.web.screens.wallet;

import com.haulmont.cuba.gui.screen.*;
import com.company.startup.model.entity.Wallet;

@UiController("startup_Wallet.browse")
@UiDescriptor("wallet-browse.xml")
@LookupComponent("walletsTable")
@LoadDataBeforeShow
public class WalletBrowse extends StandardLookup<Wallet> {
}