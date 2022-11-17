package com.company.startup.model.constants;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TradePair implements EnumClass<String> {

    NEARUSDT("NEARUSDT");

    private String id;

    TradePair(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TradePair fromId(String id) {
        for (TradePair at : TradePair.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}