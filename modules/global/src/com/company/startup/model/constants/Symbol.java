package com.company.startup.model.constants;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Symbol implements EnumClass<String> {

    USDT("USDT"),
    NEAR("NEAR");

    private String id;

    Symbol(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Symbol fromId(String id) {
        for (Symbol at : Symbol.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}