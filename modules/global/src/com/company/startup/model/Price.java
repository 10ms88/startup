package com.company.startup.model;

import com.haulmont.cuba.core.entity.BaseLongIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "STARTUP_PRICE")
@Entity(name = "startup_Price")
public class Price extends BaseLongIdEntity implements HasUuid {
    private static final long serialVersionUID = 892952325164037390L;

    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "PAIR")
    private String pair;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}