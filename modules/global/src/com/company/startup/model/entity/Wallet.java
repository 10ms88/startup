package com.company.startup.model.entity;

import com.company.startup.model.constants.TradeAction;
import com.company.startup.model.constants.TradePair;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "STARTUP_WALLET")
@Entity(name = "startup_Wallet")
public class Wallet extends StandardEntity {
    private static final long serialVersionUID = -4495874770681826704L;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "PRICE_TO_ACTION")
    private Double priceToAction;

    @Column(name = "ASSET")
    private Double asset;

    @Column(name = "TRADE_PAIR")
    private String tradePair;

    @Column(name = "ACTION_STATUS")
    private String actionStatus;

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setPriceToAction(Double priceToAction) {
        this.priceToAction = priceToAction;
    }

    public Double getPriceToAction() {
        return priceToAction;
    }

    public void setAsset(Double asset) {
        this.asset = asset;
    }

    public Double getAsset() {
        return asset;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair == null ? null : tradePair.getId();
    }

    public TradeAction getActionStatus() {
        return actionStatus == null ? null : TradeAction.fromId(actionStatus);
    }

    public void setActionStatus(TradeAction actionStatus) {
        this.actionStatus = actionStatus == null ? null : actionStatus.getId();
    }

    public TradePair getTradePair() {
        return tradePair == null ? null : TradePair.fromId(tradePair);
    }

}