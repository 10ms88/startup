package com.company.startup.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;

public class HistorySummation {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HistorySummation.class);

    public static int[] sumHistory(String json) {


        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("Error", e);
        }
        JsonNode dataNode = rootNode.get("data");

        int[] sum = new int[9];  // Array to store the sums

        // Iterate over the data array
        for (JsonNode entry : dataNode) {
            JsonNode historyNode = entry.get("history");

            // Iterate over the history entries
            for (JsonNode historyEntry : historyNode) {
                // Sum the required fields except for "dt"
                sum[0] += historyEntry.get("ordersCount").asInt();
                sum[1] += historyEntry.get("buyoutsCount").asInt();
                sum[2] += historyEntry.get("addToCartCount").asInt();
                sum[3] += historyEntry.get("buyoutsSumRub").asInt();
                sum[4] += historyEntry.get("buyoutPercent").asInt();
                sum[5] += historyEntry.get("cartToOrderConversion").asInt();
                sum[6] += historyEntry.get("addToCartConversion").asInt();
                sum[7] += historyEntry.get("openCardCount").asInt();
                sum[8] += historyEntry.get("ordersSumRub").asInt();
            }
        }

        return sum;
    }
}