package com.company.startup.core.service;

import com.company.startup.model.dto.HistoryWbRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service(WbService.NAME)
public class WbServiceBean implements WbService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WbServiceBean.class);

    private int[] sum;


    @Override
    public String getStatistic(boolean isAll) {
        HttpResponse<JsonNode> response = getStatFromWb(isAll);
        sum = HistorySummation.sumHistory(response.getBody().toString());
        return generateMessage(sum);
    }

    @Override
    public String getStocks() {
        List<String> orders = new ArrayList<>();
        HttpResponse<JsonNode> response = null;
        int sumInWayToClient = 0;
        int sumInWayFromClient = 0;
        int sumQuantityFull = 0;
        try {
            response = Unirest.get("https://statistics-api.wildberries.ru/api/v1/supplier/stocks?dateFrom=2023-09-01")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", WbService.STATISTIC_TOKEN)
                    .asJson();

            JSONArray jsonArray = response.getBody().getArray();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sumInWayToClient += jsonObject.getInt("inWayToClient");
                sumInWayFromClient += jsonObject.getInt("inWayFromClient");
                sumQuantityFull += jsonObject.getInt("quantityFull");
            }


        } catch (UnirestException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("К клиенту: ").append(sumInWayToClient).append("\n");
        stringBuilder.append("От клиента: ").append(sumInWayFromClient).append("\n");
        stringBuilder.append("Всего: ").append(sumQuantityFull);


        return stringBuilder.toString();
    }

    @Override
    public String getOrders() {
        List<String> orders = new ArrayList<>();
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://statistics-api.wildberries.ru/api/v1/supplier/orders?dateFrom=2023-09-01")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", WbService.STATISTIC_TOKEN)
                    .asJson();


            JSONArray jsonArray = response.getBody().getArray();

            jsonArray.forEach(o -> {

                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    JSONObject jsonObject = (JSONObject) o;
                    String date = formatDateString(jsonObject.getString("date"));
                    String oblast = jsonObject.getString("oblast");


                    boolean cancel = jsonObject.getBoolean("isCancel");

                    //   String orderType = jsonObject.getString("orderType");

                    String isCancel = cancel ? " (oтменен)" : "";

                    stringBuilder.append(date + "\n");
                    stringBuilder.append(oblast + isCancel + "\n");
                    orders.add(stringBuilder.toString());

                } catch (Exception e) {
                    orders.add("exception");

                }
            });


        } catch (UnirestException e) {
            log.error("Error", e);
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < orders.size(); i++) {
            res.append(i + 1).append(") ").append(orders.get(i));
        }

        return res.toString();
    }


    public String formatDateString(String originalDateString) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

        try {
            Date date = originalFormat.parse(originalDateString);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpResponse<JsonNode> getStatFromWb(boolean isAll) {

        HistoryWbRequest request = new HistoryWbRequest();


        if (isAll)            request.period.begin = request.period.end;


        try {
            return Unirest.post("https://suppliers-api.wb.ru/content/v1/analytics/nm-report/grouped/history")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", WbService.STANDARD_TOKEN)
                    .body(request.toJson())
                    .asJson();


        } catch (UnirestException e) {
            log.error("Error", e);
        }

        return null;
    }


    private String generateMessage(int[] sum) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Количество переходов в КТ: ").append(sum[7]).append("\n");
        stringBuilder.append("Заказали товаров, шт: ").append(sum[0]).append("\n");
        stringBuilder.append("Выкупили товаров, шт.: ").append(sum[1]).append("\n");
        stringBuilder.append("Положили в корзину, штук: ").append(sum[2]).append("\n");
        stringBuilder.append("Выкупили на сумму, руб.: ").append(sum[3]).append("\n");
        stringBuilder.append("Процент выкупа, % : ").append(sum[4]).append("\n");
        stringBuilder.append("Конверсия в заказ, %: ").append(sum[5]).append("\n");
        stringBuilder.append("Конверсия в корзину, %: ").append(sum[6]).append("\n");
        stringBuilder.append("Заказали на сумму, руб.: ").append(sum[8]).append("\n");

        return stringBuilder.toString();

    }


}


