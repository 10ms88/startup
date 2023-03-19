package com.company.startup.core.service;

import com.company.startup.core.connector.client.impl.SpotClientImpl;
import com.company.startup.model.constants.PrivateConfig;
import com.company.startup.model.constants.TradePair;
import com.company.startup.model.entity.PriceHistory;
import com.company.startup.utils.DateUtils;
import com.company.startup.utils.ExceptionUtils;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


@Service(PriceService.NAME)
public class PriceServiceBean implements PriceService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Persistence persistence;
    private long startTs;

    @Override
    public void collectPriceHistory(int fromDaysAgo, String interval, TradePair tradePair) {
        Date dateStart = DateUtils.truncateDate(DateUtils.minusDays(new Date(), fromDaysAgo));
        long finishTs = DateUtils.truncateDate(new Date()).getTime();
        startTs = 1677608100000L;

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        parameters.put("symbol", tradePair.getId());
        parameters.put("interval", interval);
        parameters.put("limit", "1000");

        CommitContext commitContext = new CommitContext();
        commitContext.setDiscardCommitted(true);

        try {
            while (startTs < finishTs) {
                parameters.put("startTime", startTs);
                String result = client.createMarket().klines(parameters);
                processingResult(result, tradePair, commitContext);

            }
        } catch (Exception e) {
            ExceptionUtils.logErrors(e);
            dataManager.commit(commitContext);
        }
        dataManager.commit(commitContext);

    }

    private void processingResult(String result, TradePair tradePair, CommitContext commitContext) {
        JSONArray JSONArray = new JSONArray(result);

        long interval = (long) ((JSONArray) JSONArray.get(1)).get(0) - (long) ((JSONArray) JSONArray.get(0)).get(0);

        Iterator iterator = JSONArray.iterator();

        while (iterator.hasNext()) {
            JSONArray data = (JSONArray) iterator.next();
            PriceHistory priceHistory = dataManager.create(PriceHistory.class);
            long ts = (long) data.get(0);

            priceHistory.setTs(ts);
            priceHistory.setPrice(Double.parseDouble((String) data.get(1)));
            priceHistory.setSymbol(tradePair.getId());
            priceHistory.setDate(DateUtils.stringTimeStampToDate(String.valueOf(ts / 1000)));
            commitContext.addInstanceToCommit(priceHistory);

            if (!iterator.hasNext())
                startTs = ts + interval;
        }

    }

    @Override
    public List<PriceHistory> getPriceList() {
        try (Transaction tx = persistence.createTransaction()) {
            return persistence.getEntityManager()
                    .createQuery("select p from PriceHistory p order by p.date", PriceHistory.class)
                    .getResultList();

        } catch (Exception e) {
            ExceptionUtils.logErrors(e);
            return null;
        }

    }
}
