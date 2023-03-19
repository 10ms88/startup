package com.company.startup.model.entity;

import com.haulmont.cuba.core.entity.BaseLongIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "PRICE_HISTORY")
@Entity(name = "PriceHistory")
public class PriceHistory extends BaseLongIdEntity implements HasUuid {
    private static final long serialVersionUID = -8584091257605673445L;

    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "TS")
    private Long ts;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "PRICE")
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_")
    private Date date;

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}