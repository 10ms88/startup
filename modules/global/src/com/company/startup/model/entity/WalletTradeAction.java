package com.company.startup.model.entity;

import com.company.startup.model.constants.TradeAction;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "STARTUP_WALLET_TRADE_ACTION")
@Entity(name = "startup_WalletTradeAction")
public class WalletTradeAction extends BaseLongIdEntity implements HasUuid {
    private static final long serialVersionUID = 3248453855776411313L;

    @Column(name = "UUID")
    private UUID uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    @Column(name = "ACTION_")
    private String action;

    @Column(name = "PRICE")
    private Double price;

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public TradeAction getAction() {
        return action == null ? null : TradeAction.fromId(action);
    }

    public void setAction(TradeAction action) {
        this.action = action == null ? null : action.getId();
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}