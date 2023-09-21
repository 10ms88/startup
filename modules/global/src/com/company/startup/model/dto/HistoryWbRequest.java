package com.company.startup.model.dto;

import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryWbRequest {

    public List<String> objectIDs = new ArrayList<>();
    public List<String> brandNames = new ArrayList<>();
    public List<String> tagIDs = new ArrayList<>();
    public Period period = new Period();
    public String timezone = "Europe/Moscow";
    public String aggregationLevel = "month";


    public String toJson() {
        brandNames.add("SMISS");
        return new GsonBuilder().create().toJson(this);
    }
}


class Period {
    public String begin = "2023-09-01";
    public String end = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

}
