package com.company.startup.model.constants;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TradeAction implements EnumClass<String> {

    SELL("SELL"),
    BUY("BUY");

    private String id;

    TradeAction(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TradeAction fromId(String id) {
        for (TradeAction at : TradeAction.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}