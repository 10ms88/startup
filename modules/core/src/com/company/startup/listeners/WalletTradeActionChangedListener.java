package com.company.startup.listeners;

import com.company.startup.model.entity.WalletTradeAction;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.app.events.EntityPersistingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component("startup_WalletTradeActionChangedListener")
public class WalletTradeActionChangedListener {

    @EventListener
    public void beforeCommit(EntityChangedEvent<WalletTradeAction, Long> event) {

    }

    @EventListener
    public void beforePersist(EntityPersistingEvent<WalletTradeAction> event) {
        event.getEntity().setTime(LocalDateTime.now());
    }
}