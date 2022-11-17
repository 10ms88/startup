package com.company.startup.model.entity;

import com.company.startup.model.constants.TradeAction;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@PublishEntityChangedEvents
@Table(name = "STARTUP_WALLET_TRADE_ACTION")
@Entity(name = "startup_WalletTradeAction")
public class WalletTradeAction extends BaseLongIdEntity implements HasUuid {
    private static final long serialVersionUID = 3248453855776411313L;

    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "PRICE")
    private Double currentPrice;

    @Column(name = "ASSET")
    private Double asset;

    @Column(name = "MONEY")
    private Double money;

    @Column(name = "ACTION_")
    private String action;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    @Column(name = "TIME_")
    private LocalDateTime time;

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setAsset(Double asset) {
        this.asset = asset;
    }

    public Double getAsset() {
        return asset;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
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