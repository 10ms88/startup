package com.company.startup.web.screens.wallet;

import com.haulmont.cuba.gui.screen.*;
import com.company.startup.model.entity.Wallet;

@UiController("startup_Wallet.edit")
@UiDescriptor("wallet-edit.xml")
@EditedEntityContainer("walletDc")
@LoadDataBeforeShow
public class WalletEdit extends StandardEditor<Wallet> {
}